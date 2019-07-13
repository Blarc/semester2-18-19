type 'a stream =
    Cons of 'a * (unit -> 'a stream)

              (**)
let rec from_list s r =
  match s with
  | [] -> Cons (r, fun () -> from_list [] r)
  | x::xs -> Cons (x, fun () -> from_list xs r)

let rec to_list n (Cons (x, s)) =
  if n == 0 then
    []
  else
    x :: to_list (n-1) (s())

let rec element_at n (Cons (x, s)) =
  if n == 0 then
    x
  else
    element_at (n-1) (s())

let head (Cons (x, s)) = x

let tail (Cons (x, s)) = s()

let rec map f (Cons (x, s)) =
  Cons (f x, fun () -> map f (s ()))

let rec from n = Cons (n, fun() -> from (n+1))

let nat = from 0

let rec fib =
  let rec f a b = Cons (a, fun() -> f a (b+a))
  in f 0 1

let rec zip f (Cons (a, b)) (Cons (c, d)) =
  Cons ((f a c), fun() -> zip f (b()) (d()))

let rec veckratniki_stevila k =
  map (fun x -> x * k) (from 0)

let rec veckratniki =
  let rec f i =
    Cons (veckratniki_stevila i, fun() -> f (i + 1))
  in f 0

let flatten input =
  let rec flat (Cons (s, ss)) neck = function
    | [] -> flat (ss ()) [] (neck @ [s])
    | (Cons (t, tt)) :: ts -> Cons (t, fun () -> flat (Cons (s, ss)) (neck @ [tt ()]) ts)
  in flat input [] []
