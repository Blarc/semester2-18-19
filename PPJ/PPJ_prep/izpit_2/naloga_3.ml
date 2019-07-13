type 'a stream = Cons of 'a * (unit -> 'a stream)

let rec from_list s r =
  match s with
  | [] -> Cons (r, fun () -> from_list [] r)
  | x::xs -> Cons (x, fun () -> from_list xs r)

let rec to_list n (Cons (x, s)) =
  if n == 0 then
    []
  else
    x :: to_list (n-1) (s())

let rec times = function
  | (a, 0) -> ""
  | (a, b) -> a ^ times(a, b-1)

let rec decode (Cons (x, s)) =
  Cons (times x, fun() -> decode (s()))

let rec divide (Cons (x, s)) c =
  if x = c then
    (divide (s()) c) + 1
  else
    1

let rec move (Cons (x, s)) n =
  if n > 1 then
    move (s()) (n-1)
  else
    (s())

let rec encode (Cons (x, s)) =
  let n = divide (s()) x in
  let stream = move (s()) n in
  Cons ((x, n), fun() -> encode stream)
