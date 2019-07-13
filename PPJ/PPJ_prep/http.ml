
type status = { version : string; code : int } ;;
let mystatus = { version = "HTTP/1.1"; code = 200 } ;;

let string_of_status s =
  s.version ^ " " ^
  string_of_int s.code ^ " " ^
  (match s.code with
    | 100 -> "Continue"
    | 200 -> "OK"
    | 301 -> "Moved Permanently"
    | 425 -> "Too Early"
    | 502 -> "Bad Gateway"
    | _ -> "")

type transferEncoding =
  | Chuncked
  | Compress
  | Deflate
  | Gzip
  | Identity

let string_of_transferEncoding te =
  match te with
    | Chuncked -> "Chunked"
    | Compress -> "Compressed"
    | Deflate -> "Deflate"
    | Gzip -> "Gzip"
    | Identity -> "Identity"

type date = {day_string: string; day: int; month: int; year: int}

type time = {hour: int; min: int; sec: int}

type dateTime = {date: date; time: time}

let string_of_date d =
  d.day_string ^ ", " ^
  string_of_int d.day ^ " " ^
  (match d.month with
    | 1 -> "Jan"
    | 2 -> "Feb"
    | 3 -> "Mar"
    | 4 -> "Apr"
    | 5 -> "May"
    | 6 -> "Jun"
    | 7 -> "Jul"
    | 8 -> "Aug"
    | 9 -> "Sep"
    | 10 -> "Oct"
    | 11 -> "Nov"
    | 12 -> "Dec"
    | _ -> "Alien"
  ) ^ " " ^
  string_of_int d.year

let string_of_time t =
  string_of_int t.hour ^ ":" ^
  string_of_int t.min ^ ":" ^
  string_of_int t.sec

let string_of_dateTime dt =
  string_of_date dt.date ^ " " ^
  string_of_time dt.time

type field =
  | Server of string
  | ContentLength of int
  | ContentType of string
  | TransferEncoding of transferEncoding
  | Date of dateTime
  | Expires of date
  | LastModified of date
  | Location of string

type reponse = {status: status; headers: field list; body: string}

let r = {
  status = {
    version="HTTP/1.1";
    code=200
  };
  headers = [
    Server "nginx/1.6.2";
    ContentLength 13;
    TransferEncoding Chuncked;
    Date {
      date = {
        day_string = "Wed";
        day = 21;
        month = 3;
        year = 2018;
      };
      time = {
        hour = 7;
        min = 28;
        sec = 56;
      }
    }
  ];
  body="Hello world!\n"
}

let string_of_field f =
  match f with
    | Server s -> "Server: " ^ s
    | ContentLength cl -> "ContentLength: " ^ string_of_int cl
    | ContentType ct -> "ContentType: "  ^ ct
    | TransferEncoding te -> "TransferEncoding: "  ^ string_of_transferEncoding te
    | Date d -> "Date: "  ^ string_of_dateTime d
    | Expires e -> "Expires: "  ^ string_of_date e
    | LastModified lm -> "LastModified: "  ^ string_of_date lm
    | Location l -> "Location: "  ^ l

let string_of_response r =
  (string_of_status r.status) ^ "\n" ^
  (String.concat "\n" (List.map string_of_field r.headers)) ^ "\n" ^
  r.body
