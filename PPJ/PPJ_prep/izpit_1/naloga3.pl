:- use_module(library(clpfd)).

abeceda([a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z]).

rotiraj(N, L, L) :-
  N #=< 0.
rotiraj(N, [H|T], B) :-
  N #> 0,
  N1 #= N-1,
  append(T, [H], A),
  rotiraj(N1, A, B).

preslikaj([X|_], [Y|_], X, Y).
preslikaj([_|T1], [_|T2], X, Y) :-
  preslikaj(T1, T2, X, Y).

cezar(L, In, Out) :-
  abeceda(A),
  rotiraj(L, A, B),
  maplist(preslikaj(A, B), In, Out).
