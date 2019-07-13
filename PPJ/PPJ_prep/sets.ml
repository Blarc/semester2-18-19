(* Pomožni tip, funkcija in signatura za lepše primerjanje. *)
type order = Less | Equal | Greater

let ocaml_cmp x y =
  let c = Pervasives.compare x y in
  if c < 0 then Less
  else if c > 0 then Greater
  else Equal

module type ORDERED =
sig
  type t
  val cmp : t -> t -> order
end

(* Specifikacija podatkovnega tipa množica. *)
module type SET =
sig
  type element
  val cmp : element -> element -> order
  type set
  val empty : set
  val member : element -> set -> bool
  val add : element -> set -> set
  val remove : element -> set -> set
  val to_list : set -> element list
  val fold : ('a -> element -> 'a) -> 'a -> set -> 'a
end

module AVLSet(M : ORDERED) : SET with type element = M.t =
struct
  type element = M.t
  let cmp = M.cmp

  type set = Empty | Node of element * int * set * set

  let empty = Empty

  let height = function
    | Empty -> 0
    | Node (_, h, _, _) -> h

  let leaf v = Node (v, 1, Empty, Empty)

  let node (v, l, r) = Node (v, 1 + max (height l) (height r), l, r)

  let rec member x = function
    | Empty -> false
    | Node (y, _, l, r) ->
      begin
        match cmp x y with
        | Equal -> true
        | Less -> member x l
        | Greater -> member x r
      end

  let rec to_list = function
    | Empty -> []
    | Node (x, _, l, r) -> to_list l @ [x] @ to_list r

  let rotateLeft = function
    | Node (x, _, a, Node (y, _, b, c)) -> node (y, node (x, a, b), c)
    | t -> t

  (* alternativni zapis s case *)
  let rotateRight = function
    | Node (y, _, Node (x, _, a, b), c) -> node (x, a, node (y, b, c))
    | t -> t

  let imbalance = function
    | Empty -> 0
    | Node (_, _, l, r) -> height l - height r

  let balance = function
    | Empty -> Empty
    | Node (x, _, l, r) as t ->
      begin
        match imbalance t with
        | 2 ->
          (match imbalance l with
           | -1 -> rotateRight (node (x, rotateLeft l, r))
           | _ -> rotateRight t)
        | -2 ->
          (match imbalance r with
           | 1 -> rotateLeft (node (x, l, rotateRight r))
           | _ -> rotateLeft t)
        | _ -> t
      end

  let rec add x = function
    | Empty -> leaf x
    | Node (y, _, l, r) as t ->
      begin
        match cmp x y with
        | Equal -> t
        | Less -> balance (node (y, add x l, r))
        | Greater -> balance (node (y, l, add x r))
      end

  let rec remove x = function
    | Empty -> Empty
    | Node (y, _, l, r) ->
      let rec removeSuccessor = function
        | Empty -> assert false
        | Node (x, _, Empty, r) -> (r, x)
        | Node (x, _, l, r) ->
          let (l', y) = removeSuccessor l in
          (balance (node (x, l', r)), y)
      in
      match cmp x y with
      | Less -> balance (node (y, remove x l, r))
      | Greater -> balance (node (y, l, remove x r))
      | Equal ->
        begin match (l, r) with
          | (_, Empty) -> l
          | (Empty, _) -> r
          | _ ->
            let (r', y') = removeSuccessor r in
            balance (node (y', l, r'))
        end

  let rec fold f x = function
    | Empty -> x
    | Node (y, _, l, r) -> fold f (f (fold f x l) y) r

end

module IntListSet : SET with type element = int =
struct
  type element = int

  let cmp = ocaml_cmp

  type set = int list

  let empty = []

  let rec member x = function
    | [] -> false
    | h :: t -> (x = h) || member x t

  let add x s = if member x s then s else (x :: s)

  let rec remove x = function
    | [] -> []
    | h :: t ->
      if h = x then t else h :: remove h t

  let to_list s = s

  let rec fold f x = function
    | [] -> x
    | h :: t -> fold f (f x h) t

end

module ListSet(M : ORDERED) : SET with type element = M.t =
struct
  type element = M.t

  let cmp = ocaml_cmp

  type set = M.t list

  let empty = []

  let rec member x = function
    | [] -> false
    | h :: t -> (x = h) || member x t

  let add x s = if member x s then s else (x :: s)

  let rec remove x = function
    | [] -> []
    | h :: t -> if h = x then t else h :: remove h t

  let to_list s = s

  let rec fold f x = function
    | [] -> x
    | h :: t -> fold f (f x h) t
end

module SL = ListSet (struct type t = int let cmp = ocaml_cmp end)
module SA = AVLSet (struct type t = int let cmp = ocaml_cmp end)

let rec add_list = function
  | 0 -> SL.empty
  | n -> SL.add n (add_list (n - 1))

let rec add_avl = function
  | 0 -> SA.empty
  | n -> SA.add n (add_avl (n - 1))

let time f =
  let start = Sys.time () in
  f () ;
  let stop = Sys.time () in
  stop -. start

module SetOps(S : SET) =
struct
  let union a b = S.fold (fun u x -> S.add x u) a b

  let filter p a = S.fold (fun b x -> if p x then S.add x b else b) S.empty a

  let intersection a b = filter (fun x -> S.member x a) b
end
