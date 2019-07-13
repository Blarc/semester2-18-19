:- use_module(library(clpfd)).

prog(A, B) :-
  length(B, A),
  B ins 1..A,
  all_different(B),
  label(B).

s([], 0).
s([X|L], S) :-
  S #= S1 + X,
  s(L, S1).

pascal(_, 0, 1).
pascal(I, J, 1) :-
  I #> 1,
  J #= I.
pascal(I, J, N) :-
  I #> 1,
  I1 #= I - 1,
  J1 #= J - 1,
  pascal(I1, J, N1),
  pascal(I1, J1, N2),
  N #= N1 + N2.
