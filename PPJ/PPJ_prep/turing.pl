is_sorted([]).
is_sorted([_]).
is_sorted([H,H2|T]) :-
  H =< H2,
  is_sorted([H2|T]).

insert(X, L, [X|L]).
insert(X, [H|T], [H|T2]) :-
  insert(X, T, T2).

action(stay, Tape, Tape).

action(left, []-R, []-[b|R]).
action(left, [L|Ls]-R, Ls-[L|R]).

action(right, L-[], [b|L]-[]).
action(right, L-[R|Rs], [R|L]-Rs).

head_rest([], b, []).
head_rest([R|Rs], R, Rs).

step(Name, L-R, State, NewL-NewR, NewState) :-
  head_rest(R, Symbol, Rs),
  program(Name, State, Symbol, NewState, NewSymbol, Action),
  action(Action, L-[NewSymbol|Rs], NewL-NewR).
