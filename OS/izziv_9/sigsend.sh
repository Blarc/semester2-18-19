#!/bin/bash

input=$1;

if [[ $input == [0-9]* ]]; then
	pid=$input;
else
	pid=$(pgrep $input);
fi

while(true)
do

	kill -0 $pid 2> /dev/null;
	if [[ "$?" -ne 0 ]]; then
		echo "Process does not exist!";
		exit 1
	fi

	read -n 1 -t 1 -s var;

	case "$var" in
		x)
			echo "bye bye!";
			exit 0;
			;;
		h)
		    echo "x - izhod iz programa";
		    echo "h - izpis pomoči";
		    echo "w - pošlje signal SIGHUP (končanje pripadajočega terminala)";
		    echo "i - SIGINT (končanje kot Ctrl+C)";
		    echo "q - SIGQUIT (končanje procesa, Ctrl+/)";
		    echo "t - SIGTERM (končanje procesa)";
		    echo "k - SIGKILL (brezpogojno končanje procesa)";
		    echo "c - SIGCONT (nadaljevanje izvajanja procesa)";
		    echo "s - SIGSTOP (brezpogojna ustavitev izvajanja procesa)";
		    echo "z - SIGTSTP (ustavitev izvajanja procesa kot Ctrl-Z)";
		    echo "1 - SIGUSR1 (1. uporabniški signal)";
		    echo "2 - SIGUSR2 (2. uporabniški signal)";
		    echo "y - SIGCHLD (končanje otroka)";
		    ;;
		w)
			kill -SIGHUP $pid 2> /dev/null;
			;;
		i)
			kill -SIGINT $pid 2> /dev/null;
			;;
		q)
			kill -SIGQUIT $pid 2> /dev/null;
			;;
		t)
			kill -SIGTERM $pid 2> /dev/null;
			;;
		k)
			kill -SIGKILL $pid 2> /dev/null;
			;;
		c)
			kill -SIGCONT $pid 2> /dev/null;
			;;
		s)
			kill -SIGSTOP $pid 2> /dev/null;
			;;
		z)
			kill -SIGTSTP $pid 2> /dev/null;
			;;
		1)
			kill -SIGUSR1 $pid 2> /dev/null;
			;;
		2)
			kill -SIGUSR2 $pid 2> /dev/null;
			;;
		y)
			kill -SIGCHLD $pid 2> /dev/null;
			;;
	esac
done

