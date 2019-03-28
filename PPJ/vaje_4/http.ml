(* Komentariji *)

type status = {version: string; code: int}

let string_of_status s =
	s.version ^ " " ^
	(string_of_int s.code) ^ " " ^
	(match s.code with
		| 102 -> "Processing"
		| 200 -> "OK"
		| 301 -> "Moved Permanently"
		| 418 -> "I'm a teapot"
		| 508 -> "Request Timeour"
		| _ -> ""
	)

type transferEncoding =
	| Chunked
	| Compress
	| Deflate
	| Gzip
	| Identity

let string_of_transferEncoding te = 
	match te with
	| Chunked -> "Chunked"
	| Compress -> "Compress"
	| Deflate -> "Deflate"
	| Gzip -> "Gzip"
	| Identity -> "Identity"

type field =
	| Server of string
	| ContentLength of int
	| ContentType of string
	| TransferEncoding of transferEncoding
	| Date of string
	| Expires of string 
	| LastModified of string 
	| Location of string 

let string_of_field f =
	match f with
		| Server s -> "Server: " ^ s
		| ContentLength cl -> "Content-Length: " ^ string_of_int cl
		| ContentType ct -> "Content-Type: " ^ ct
		| TransferEncoding te -> "Transfer-Encoding: " ^ string_of_transferEncoding te
		| Date date -> "Date: " ^ date
		| Expires exp -> "Expires: " ^ exp
		| LastModified lm -> "Last-Modified: " ^ lm
		| Location loc -> "Location: " ^ loc

type response = {status: status; headers: field list; body: string}

let string_of_response r =
	(string_of_status r.status) ^ "\n" ^
	(String.concat "\n" (List.map string_of_field r.headers)) ^ "\n\n" ^
	r.body	



let mystatus = {version = "HTTP/1.1"; code = 418};;

let myresponse = {status = mystatus;
				 headers = [Server "nginx/1.6.2";
							ContentLength 13;
							ContentType "text/html";
							TransferEncoding Chunked;
							Date "Fri, 23 Mar 2018 17:43:15 GMT";
							Expires "Sun, 19 Nov 1978 05:00:00 GMT"];
				 body = "Hello world!\n"};;

(* print_string (string_of_status mystatus^"\n") *)

print_string (string_of_response myresponse^"\n")