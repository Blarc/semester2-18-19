type date =
  {
    year : int;
    month : int;
    day : int;
    h : int;
    min : int;
    sec : int;
  }

type email = {
  sender : string;
  receivers : string list;
  date : date;
  subject : string;
  message : string;
}

let string_of_date d =
  string_of_int d.year ^"-"^
  string_of_int d.month ^"-"^
  string_of_int d.day ^"-"^
  string_of_int d.h ^"-"^
  string_of_int d.min ^"-"^
  string_of_int d.sec

let rec string_of_receivers = function
  | [] -> ""
  | h::l -> h ^", " ^ string_of_receivers l

let string_of_email e =
  "From : " ^ e.sender ^ "\n" ^
  "To: " ^ string_of_receivers e.receivers ^ "\n" ^
  "Date: " ^ string_of_date e.date ^ "\n" ^
  "Subject: " ^ e.subject ^ "\n\n" ^
  e.message

let my_email = {
  sender = "Andrej Bauer <Andrej.Bauer@andrej.com";
  receivers = ["Timotej Lazar <Timotej.Lazar@fri.uni-lj.si"; "Peter Gabrovšek <Peter.Gabrovšek@fri.uni-lj.si>"];
  date = {
    year = 2018;
    month = 5;
    day = 29;
    h = 9;
    min = 55;
    sec = 42;
  };
  subject = "Izpit iz PPJ";
  message = "Prosim, da rešitve izpita popravljata zelo stroho.\n\nLep pozdrav, Andrej\n";
}
