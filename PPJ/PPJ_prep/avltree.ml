type height = int
type value = int

type avltree =
  | Empty
  | Node of value * height * avltree * avltree

let t =
    Node (5, 3,
        Node (3, 2,
            Node(1, 1, Empty, Empty),
            Node(4, 1, Empty, Empty)
        ),
        Node (8, 1, Empty, Empty)
    )

let height = function
  | Empty -> 0
  | Node (_, h, _, _) -> h

let leaf v = Node (v, 1, Empty, Empty)

let node (v, l, r) = Node (v, 1 + max (height l) (height r), l, r)

let rec toList = function
  | Empty -> []
  | (Node (x, _, l, r)) -> toList l @ [x] @ toList r

type order = Less | Equal | Greater

let cmp x y =
  match compare x y with
  | 0 -> Equal
  | r when r < 0 -> Less
  | _ -> Greater

let rec search x = function
  | Empty -> false
  | Node (y, _, l, r) ->
      match cmp x y with
      | Equal -> true
      | Less -> search x l
      | Greater -> search x r

let rotateLeft = function
  | Node (x, _, a, Node (y, _, b, c)) -> node(y, node(x, a, b), c)
  | t -> t

let rotateRight = function
  | Node (y, _, Node (x, _, a, b), c) -> node (x, a, node(y, b, c))
  | t -> t

let imbalance = function
  | Empty -> 0
  | Node (_, _, l, r) -> height l - height r

let balance = function
  | Empty -> Empty
  | Node (x, _, l, r) as t ->
    match imbalance t with
    | 2 ->
      (
        match imbalance l with
        | -1 -> rotateRight (node (x, rotateLeft l, r))
        | _ -> rotateLeft t
      )
    | -2 ->
      (
        match imbalance r  with
        | 1 -> rotateLeft (node (x, l, rotateRight r))
        | _ -> rotateRight t
      )
    | _ -> t

let rec add x = function
  | Empty -> leaf x
  | Node (y, _, l, r) as t ->
    match cmp x y with
    | Equal -> t
    | Less -> balance (node (y, add x l, r))
    | Greater -> balance (node (y, l, add x r))

let rec remove x = function
  | Empty -> Empty
  | Node (y, _, l, r) ->
    let rec removeSuccessor = function
      | Empty -> failwith "impossible"
      | Node (x, _, Empty, r) -> (r, x)
      | Node (x, _, l, r) ->
        let (l', y) = removeSuccessor l in
        (balance (node (x, l', r)), y)
    in
    match cmp x y with
    | Less -> balance (node (y, remove x l, r))
    | Greater -> balance (node (y, l, remove x r))
    | Equal ->
      match (l, r) with
      | (_, Empty) -> l
      | (Empty, _) -> r
      | _ -> let (r', y') = removeSuccessor r in
        balance (node (y', l, r'))
