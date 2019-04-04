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

let toList = function
	| Empty -> []
	| Node 