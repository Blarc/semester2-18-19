#!/bin/bash

pipe=streznik.cev

trap "rm -f $pipe" EXIT

if [[ ! -p "$pipe" ]]; then
	mkfifo $pipe
fi

a="Hello hello";
comm=$(awk '{print $1}' <<< $a);
echo $comm;

user=""
echo "Server started..."
while true
do
	if read line <"$pipe"; then
		split=( $line );
		comm="${split[0]}"
		if [[ $comm == 'add' ]]; then
			(( result = 0 ))
			for word in "${split[@]:1}"; do
				(( result += word ));
			done
			echo "Sum: $result";
		elif [[ $comm == 'mult' ]]; then
			(( result = 1 ))
			for word in "${split[@]:1}"; do
				(( result *= word ));
			done
			echo "Product: $result"
		elif [[ $comm == 'connect' ]]; then
			user="${split[1]}"
			result="Connected to $pipe."
			echo "$user connected."
		elif [[ $comm == 'disconnect' ]]; then
			user=""
			result="Disconnected from the $pipe."
			echo "$user disconnected."
		else
			result="Wrong arguments!"
			echo "Wrong arguments!"
		fi

		echo "$result" >$pipe;
	fi
done

echo "Closing the server...";

		