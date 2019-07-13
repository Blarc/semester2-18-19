let vsota_lihih_41 =
  let v = ref 0 in
  let i = ref 0 in
  while !i < 41 do
    v := !v + (2 * !i + 1) ;
    i := !i + 1
  done;
  !v

let vsota1 n =
  let v = ref 0 in
  let i = ref 0 in
  while !i <= n do
    v := !v + !i;
    i := !i + 1
  done;
  !v

let rec vsota2 = function
  | 0 -> 0
  | n -> n + vsota2 (n-1)

let rec vsota3 n =
  let rec vsota v i =
    if i <= n then
      vsota (v + i) (i + 1)
    else
      v
  in
  vsota 0 0

let fibonacci1 n =
  let one = ref 0 in
  let two = ref 1 in
  for i = 1 to n do
    let tmp = !one + !two in
    one := !two;
    two := tmp;
  done ;
  !one

let rec fibonacci2 = function
  | 0 -> 0
  | 1 -> 1
  | n -> fibonacci2 (n - 1) + fibonacci2 (n - 2)

let fibonacci3 n =
  let rec fibonacci a b i =
    if i < n then
      fibonacci b (a + b) (i + 1)
    else
      a
  in
  fibonacci 0 1 0

let vsota_lihih1 n =
  let v = ref 0 in
  let i = ref 0 in
  while !i < n do
    v := !v + (2 * !i + 1);
    i := !i + 1
  done ;
  !v

let vsota_lihih2 n =
  let rec vsota v i =
    if i < n then
      vsota (v + (2 * i + 1)) (i + 1)
    else
      v
  in
  vsota 0 0

let zanka s0 p f r =
  let rec loop s =
    if p s then loop (f s) else r s
  in
  loop s0

let vsota4 n =
  zanka
    (0, 0)
    (fun (_, i) -> i <= n)
    (fun (s, i) -> (s + i, i + 1))
    (fun (s, _) -> s)

let fibonacci4 n =
  zanka
    (0, 1, 0)
    (fun (_, _, i) -> i < n)
    (fun (a, b, i) -> (b, a + b, i + 1))
    (fun (a, _, _) -> a)

let forzanka s0 a b f r =
  let rec loop s i =
    if i > b then
      r s
    else
      loop (f s) (i+1)
  in
  loop s0 a

let fibonacci5 n =
  forzanka
    (0, 1)
    1
    n
    (fun (a, b) -> (b, a + b))
    (fun (a, _) -> a)
