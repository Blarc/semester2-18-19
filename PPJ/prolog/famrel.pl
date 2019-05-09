parent(tina, william).
parent(thomas, william).
parent(thomas, sally).
parent(thomas, jeffrey).
parent(sally, andrew).
parent(sally, melanie).
parent(andrew, joanne).
parent(jill, joanne).
parent(joanne, steve).
parent(william, vanessa).
parent(william, patricia).
parent(vanessa, susan).
parent(patrick, susan).
parent(patricia, john).
parent(john, michael).
parent(john, michelle).

parent(frank, george).
parent(estelle, george).
parent(morty, jerry).
parent(helen, jerry).
parent(jerry, anna).
parent(elaine, anna).
parent(elaine, kramer).
parent(george, kramer).

parent(margaret, nevia).
parent(margaret, alessandro).
parent(ana, aleksander).
parent(aleksandr, aleksander).
parent(nevia, luana).
parent(aleksander, luana).
parent(nevia, daniela).
parent(aleksander, daniela).

male(william).
male(thomas).
male(jeffrey).
male(andrew).
male(steve).
male(patrick).
male(john).
male(michael).
male(frank).
male(george).
male(morty).
male(jerry).
male(kramer).
male(aleksandr).
male(alessandro).
male(aleksander).

female(tina).
female(sally).
female(melanie).
female(joanne).
female(jill).
female(vanessa).
female(patricia).
female(susan).
female(michelle).
female(estelle).
female(helen).
female(elaine).
female(anna).
female(margaret).
female(ana).
female(nevia).
female(luana).
female(daniela).

% relacije

mother(X, Y) :-
	female(X),
	parent(X, Y).
	
child(X, Y) :- parent(Y, X).

grandparent(X, Y) :-
	parent(X, Z),
	parent(Z, Y).
	
sister(X, Y) :-
	parent(Z, X),
	parent(Z, Y),
	female(X),
	X \= Y.

aunt(X, Y) :-
	sister(X, Z),
	parent(Z, Y).
	
ancestor(X, Y) :- parent(X, Y).
ancestor(X, Y) :-
	parent(X, Z),
	ancestor(Z, Y).
	
descendant(X, Y) :- ancestor(Y, X).

% SEZNAMI

ancestor(X, Y, [X, Y]) :- parent(X, Y).
ancestor(X, Y, [X|L]) :-
	parent(X, Z),
	ancestor(Z, Y, L).
	
descendant(X, Y, L) :- ancestor(Y, X, L).

member(X, [X|_]).
member(X, [_|T]) :- member(X, T).

insert(X, L1, [X|L1]).
insert(X, [H|T], [H|L]) :-
	insert(X, T, L).
	
reverse([], []).
reverse([H|T], L) :-
	reverse(T, R),
	append(R, [H], L).

is_sorted([]).
is_sorted([_]).
is_sorted([H1, H2|T]) :-
	H1 =< H2,
	is_sorted([H2|T]).

permute([], []).
permute([H|T], B) :-
	permute(T, P),
	insert(H, P, B).
	
bogosort(A, B) :-
	permute(A, B),
	is_sorted(B).

	
% TURINGOVI STROJI

program(plus1, q0, 1, q0, 1, right).
program(plus1, q0, b, final, 1, stay).

program(copy, q0, b, final, b, stay).
program(copy, q0, 1, 2, b, right).
program(copy, 2, b, 3, b, right).
program(copy, 2, 1, 2, 1, right).
program(copy, 3, b, 4, 1, left).
program(copy, 3, 1, 3, 1, right).
program(copy, 4, b, 5, b, left).
program(copy, 4, 1, 4, 1, left).
program(copy, 5, b, q0, 1, right).
program(copy, 5, 1, 5, 1, left).

action(stay, Tape, Tape).
action(right, L-[H|T], [H|L] - T).
action(right, L-[], [b|L] - []).
action(left, [H|T]-R, T-[H|R]).
action(left, []-R, []-[b|R]).

head_rest([H|T], H, T).
head_rest([], b, []).


step(Name, InL-InR, InState, OutL-OutR, OutState) :-
	head_rest(InR, H, R),
	program(Name, InState, H, OutState, A, Direction),
	action(Direction, InL-[A|R], OutL-OutR).

run(_, final, InTape, InTape).
run(Name, State, InTape, OutTape) :-
	step(Name, InTape, State, TempTape, TempState),
	run(Name, TempState, TempTape, OutTape).
	
turing(Name, InTape, OutTape) :-
	run(Name, q0, []-InTape, L-R),
	reverse(L, ReveresedL),
	append(ReveresedL, R, OutTape).









