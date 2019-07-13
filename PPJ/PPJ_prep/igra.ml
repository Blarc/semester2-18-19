(** Primer objektnega programiranja v OCamlu. *)

(** Antagonistična igra za dva igralca s polno informacijo:

    - antagonistična: igralca sta nasprotnika, ne sodelujeta
    - polna informacija: stanje igre je popolnama znano, v igri ni verjetnostnih dogodkov

    Dodatno predpostavimo, da ni neodločenega rezultata in da izgubi tisti, ki se ne more
    premakniti.
 *)

(** Enemu igralcu pravimo [Player] in drugemu [Oppoennt] *)
type player = Player | Opponent

(** Pretvori igralca v string. *)
let string_of_player = function
  | Player -> "Player"
  | Opponent -> "Opponent"

(** Nasprotni igralec *)
let other = function
  | Player -> Opponent
  | Opponent -> Player

exception Invalid_move

(** Igro predstavimo kot objekt *)
type 'move game =
  <
  to_move : player ;
  (** Kdo je na potezi *)

  moves : 'move list ;
  (** Seznam trenutno veljavnih potez. *)

  string_of_move : 'move -> string ;
  (** Pretvori potezo v string *)

  move_of_string : string -> 'move ;
  (** Pretvori string v potezo *)

  play : 'move -> unit ;
  (** Odigraj dano potezo, sproži [Invalid_move], če poteza ni veljavna. *)

  undo : unit ;
  (** Prekliči potezo. *)

  show : unit ;
  (** Prikaži stanje igre na zaslonu. *)
  >

(** Igrica tri v vrsto. *)
module TicTacToe =
  struct

    (** Polje je lahko prazno, ali pa ga zaseda eden od igralcev. *)
    type field = Empty | Full of player

    (** Igrica tri v vrsto, predstavljena kot objekt. *)
    let game : (int * int) game =
      object (self)
        val board = Array.make_matrix 3 3 Empty ; (** Igralno polje *)
        val mutable to_move = Player ; (** Kdo je trenutno na potezi *)
        val mutable history = [] ; (** Seznam do sedaj odigranih potez *)

        method to_move = to_move

        method string_of_move (i, j) =
          Format.sprintf "(%d, %d)" i j

        method move_of_string s =
          Scanf.sscanf s "(%d, %d)" (fun i j -> (i, j))

        (** Ali je igra končana? *)
        method private is_over =
          let winning ((i1,j1), (i2,j2), (i3,j3)) =
            board.(i1).(j1) = board.(i2).(j2) &&
              board.(i1).(j1) = board.(i3).(j3) &&
                board.(i1).(j1) <> Empty
          in
          List.for_all
            (fun (i,j) -> board.(i).(j) <> Empty)
            [ (0,0); (1,0); (2,0);
              (0,1); (1,1); (2,1);
              (0,2); (1,2); (2,2) ]
          ||
            List.exists winning
              [ ((0,0), (0,1), (0,2)) ;
                ((1,0), (1,1), (1,2)) ;
                ((2,0), (2,1), (2,2)) ;
                ((0,0), (1,0), (2,0)) ;
                ((0,1), (1,1), (2,1)) ;
                ((0,2), (1,2), (2,2)) ;
                ((0,0), (1,1), (2,2)) ;
                ((0,2), (1,1), (2,0)) ]

        method moves =
          if self#is_over then
            []
          else
            let ms = ref [] in
            for i = 0 to 2 do
              for j = 0 to 2 do
                match board.(i).(j) with
                | Empty -> ms := (i,j) :: !ms
                | Full _ -> ()
              done
            done ;
            assert (!ms <> []) ;
            !ms

        method play (i, j) =
          match board.(i).(j) with
          | Full _ -> raise Invalid_move
          | Empty ->
             board.(i).(j) <- Full to_move ;
             history <- (i,j) :: history ;
             to_move <- other to_move

        method undo =
          match history with
          | [] -> assert false
          | (i,j) :: hs ->
             history <- hs ;
             to_move <- other to_move ;
             board.(i).(j) <- Empty

        method show =
          Format.printf "+---+---+---+\n" ;
          for i = 0 to 2 do
            Format.printf "|" ;
            for j = 0 to 2 do
              let c =
                match board.(i).(j) with
                | Empty -> ' '
                | Full Player -> 'P'
                | Full Opponent -> 'O'
              in
              Format.printf " %c |" c
            done ;
            Format.printf "\n+---+---+---+\n"
          done ;
          Format.printf "@."
      end
  end

(** Agent, ki zna igrati igrico, je objekt z metodo [move]. *)

(** Človeški agent igra tako, da vprašamo, katero potezo naj odigra. *)
let human (game : 'move game) =
  object

    method move =
      Format.printf "Available moves:@\n" ;
      List.iter (fun m -> Format.printf "%s " (game#string_of_move m)) game#moves ;
      Format.printf "@\nEnter your move:@." ;
      game#move_of_string (read_line ())

  end

(** Računalniški agent pregelda celotno igro in naključno izbere eno
    od zmagovalnih potez. Če take poteze ni, izbere naključno potezo. *)
let computer (game : 'move game) =
  object (self)

    (** Izvedi potezo [m], izračunaj [f ()], nato pa prekliči potezo in vrni rezultat. *)
    method private after_move m f =
      game#play m ;
      let r = f () in
      game#undo ;
      r

    (** Ali je poteza [m] zmagovalna? *)
    method private is_winning m =
      self#after_move m
        (fun () -> List.for_all self#is_losing game#moves)

    (** Ali poteza [m] izgubi proti optimalnemu nasprotniku? *)
    method private is_losing m =
      self#after_move m
        (fun () -> List.exists self#is_winning game#moves)

    (** Seznam zmagovalnih potez *)
    method private winning_moves =
      List.filter self#is_winning game#moves

    method move =
      let ms =
        match self#winning_moves with
        | [] ->
           begin match game#moves with
           | [] -> failwith "No moves"
           | ms -> ms
           end
        | _::_ as ms -> ms
      in
      let k = Random.int (List.length ms) in
      List.nth ms k

  end

(** Odigraj dano igro z danima agentoma *)
let play game player opponent =
  let ask_move p =
    match game#moves with
    | [] -> None
    | _::_ -> Some (p#move)
  in
  let pl = player game
  and op = opponent game in
  let rec loop () =
    game#show ;
    let m =
      match game#to_move with
      | Player -> ask_move pl
      | Opponent -> ask_move op
    in
    match m with
    | None ->
       Format.printf "%s won!@." (string_of_player (other game#to_move))
    | Some m ->
       Format.printf "%s plays %s@." (string_of_player game#to_move) (game#string_of_move m) ;
       game#play m ;
       loop ()
  in
  loop ()

;;

(** Glavni program *)

let main =
  Random.self_init () ;
  play TicTacToe.game human computer
