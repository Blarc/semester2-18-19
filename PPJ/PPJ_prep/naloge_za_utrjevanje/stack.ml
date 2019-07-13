module type STACK =
sig
  type element
  type stack
  val empty : stack
  val push : element -> stack -> stack
  val pop : stack -> element * stack
end

type t
module ListStack : STACK with type element = t =
struct
  type element = t

  type stack = element list

  let empty = []

  let push e s = (e::s)

  let pop (x::s) = (x, s)
end
