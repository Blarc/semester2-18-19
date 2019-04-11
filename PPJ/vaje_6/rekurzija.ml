let vsota_lihih_42 =
	let v = ref 0 in
		let i = ref 0 in
			while !i < 42 do
				v := !v + (2 * !i + 1);
				i := !i + 1
			done;
			!v
			
let vsota_lihih_42_2 =
	let v = ref 0 in
		for i = 0 to 41 do
			v := !v + i
		done;
		!v

(* ZANKE *)

(* 1 + 2 + ... + n *)
let vsota1 n =
	let v = ref 0 in 
	let i = ref 0 in
	while !i < n do
		v := !v + !i;
		(* i := !i + 1 *)
		incr i
	done;
	!v

(*  
	f(0) = 0
	f(1) = 1
	f(n) = f(n-1) + f(n-2)
*)

let fibonacci1 n =
	let a = ref 0 in
	let b = ref 1 in
	let i = ref 0 in
	while !i < n do
		let b' = !a + !b  in
		a := !b;
		b := b';
		incr i
	done;
	!a
	
(* REKURZIJA *)
let rec vsota2 = function
	| 0 -> 0
	| n -> n + vsota2 (n - 1)


let rec fibonacci2 = function
	| 0 -> 0
	| 1 -> 1
	| n -> fibonacci2 (n - 1) + fibonacci2 (n - 2)


(* REPNA REKURZIJA *)	
(*
let rec f = function
	| 0 -> 1
	| n ->
		if n mod 2 = 0 then
			f (n / 2)
		else
			3 * f (n - 1)
*)

let vsota_lihih_1 n =
	let v = ref 0 in
		let i = ref 0 in
			while !i < n do
				v := !v + (2 * !i + 1);
				i := !i + 1
			done;
			!v

let vsota_lihih_2 n =
	let rec vsota v i =
		if i < n then
			vsota (v + (2 * i + 1)) (i + 1)
		else
			v
	in
	vsota 0 0
	
let fibonacci3 n =
	let rec fib a b i =
		if i < n then
			fib b (a + b) (i + 1)
		else
			a
	in
	fib 0 1 0
	
(* PRETVORBA WHILE V REKURZIJO *)

let zanka s0 p f r =
	let rec loop s =
		if p s then
			loop(f s)
		else
			r s
	in
	loop s0
	
let vsota4 n =
	zanka
		(0, 1) (* v, i *)
		(fun (v, i) -> i <= n)
		(fun (v, i) -> v + i, i + 1)
		(fun (v, _) -> v)

let fibonacci4 n =
	zanka
		(0, 1, 0) (* a, b, i *)
		(fun (_, _, i) -> i < n)
		(fun (a, b, i) -> b, a + b, i + 1)
		(fun (a, _, _) -> a)


(* PRETVORBA FOR v RAKURZIJO *)

let forzanka s0 a b f r =
	let rec loop s i =
		if i > b then
			r s
		else
			loop (f i s) (i + 1)
	in
	loop s0 a
	

let vsota5 n =
	forzanka
		0 (* v *)
		1
		n
		(fun i v -> v + i)
		(fun v -> v)
		
let fibonacci5 n =
	forzanka
		(0, 1) (* a, b, i *)
		1
		n
		(fun i (a, b) -> b, a + b)
		(fun (a, _) -> a)



























