type value = int
type height = int

type avlTree =
	| Empty
	| Node of value * height * avlTree * avlTree

let t =
	Node (5, 3,
		Node (3, 2,
			Node (1, 1, Empty, Empty),
			Node (4, 1, Empty, Empty)
		),
		Node (8, 1, Empty, Empty)
	)

let height = function
	| Empty -> 0
	| Node (_, h, _, _) -> h

let leaf v = Node(v, 1, Empty, Empty)

let node (v, l, r) = Node (v, 1 + max (height l) (height r), l, r)

let t' = 
	node (5,
		node(3, leaf 1, leaf 4),
		leaf 8
	)

let rec toList = function
	| Empty -> []
	| Node (v, _, l, r) -> toList l @ [v] @ toList r

let cmp x y =
	match compare x y with
		| 0 -> Equal
		| r when r < 0 -> Less
		| _ -> Greater

let search x = function
	| Empty -> false
	| Node (y, _, l, r) ->
		begin match cmp x y with
			| Equal -> true
			| Less -> search x l  
			| Greater -> search x r
		end

let rotateLeft = function
	| Node (x, _, a, Node (y, _, b, c)) ->
		node (y, node(x, a, b), c)
	| t -> t

let rotateRight = function
	| Node (y, _, Node (x, _, a, b), c) ->
		node(x, a, node(y, b, c))
	| t -> t

let imbalance = function
	| Empty -> 0
	| Node (_, _, l, r) -> height l - height r

let balance = function
	| Empty -> Empty
	| Nod (x, _, l, r) as t ->
		begin match imbalance t with
			| 2 ->
				begin match imbalance l with
					| -1 -> rotateRight (node (x, rotateLeft, l, r))
					| _ -> rotateRight t
				end
			| -2 ->
				begin match imbalance r with
					| 1 -> rotateLeft (node (x, l, rotateRight r))
					| _ -> rotateLeft t
				end
			| _ -> t
		end


let t'' = node (5, 
		node (3, 
			node(1, leaf 0, Empty), 
			leaf 4
		), 
		leaf 8
	)


let rec add x = function
	| Empty -> leaf x
	| Node (y, _, l, r) ->
		begin match cmp x y with
			| Equal -> t
			| Less -> balance(node (y, add x l, r))
			| Greater -> balance(node (y, l, add x r)) 
		end


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
			| Less -> balance (node(y, remove x l, r))
			| Greater -> balance (node(y, l, remove x r))
			| Equal -> 
				begin match (l, r) with
					| (_, Empty) -> l
					| (Empty, _) -> r
					| _ -> let (r', y') = removeSuccessor r in
						balance (node(y', l, r'))
				end


				
	
