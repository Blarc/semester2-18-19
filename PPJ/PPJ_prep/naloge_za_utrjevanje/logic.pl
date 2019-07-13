:- use_module(library(clpfd)).

bought(eva, bread, 1).      % Eva je kupila 1 kg kruha
bought(tomi, beer, 6).      % Tomi je kupil 6 litrov piva
bought(eva, butter, 2).     % Eva je kupila tudi 2 kg jabolk
bought(tina, beer, 2).
bought(tina, salami, 1).

price(beer, 2).    % liter piva stane 2 €
price(apples, 1).  % kg jabolk pa 1 €
price(bread, 3).
price(salami, 17).

two(X) :-
  bought(X, R, _),
  bought(X, P, _),
  P \== R.

all_equal([]).
all_equal(_).
all_equal([H1,H2|T]) :-
  H1 = H2,
  all_equal(H2|T).

letter(a, [2]).
letter(t, [8]).
letter(o, [6,6,6]).
letter(u, [8,8]).
letter(v, [8,8,8]).

spell([], []).
spell([H|T], L) :-
    letter(H, S),
    spell(T, LT),
    append(S, LT, L).

letter(L) :-
  member(L, [a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z]).

word([c,h,a,i,r]).
word([c,h,e,s,s]).
word([d,e,s,k]).
word([c,h,a,l,k]).
word([c,h,o,p]).
word([d,i,s,k]).

insert(X, L, [X|L]).
insert(X, [H|T], [H|T2]) :-
  insert(X, T, T2).

edit(Word, New) :-
    insert(_, New, Word).
edit(Word, New) :-
    letter(L),
    insert(L, Word, New).
edit(Word, New) :-
    letter(L),
    append(A, [_|B], Word),
    append(A, [L|B], New).
