#!/bin/bash

cmd="comm,";
mem="%mem,";
usr="user,";
cpu="%cpu";

while(true)
do
	flags="pid,$cmd$mem$usr$cpu";
	ps -o "$flags";
	read -n 1 -t 1 -s var;

	case "$var" in
		q)
			echo "bye bye!";
			exit 0;
			;;
		h)
			echo "-------------";
			echo "| help page |";
			echo "-------------";

			echo "q - quit";
			echo "h - help";
			echo "c - command toggle display";
			echo "m - memory toggle display";
			echo "p - cpu toggle display";
			echo "u - user toggle display";
			read -n 1 -s;
			;;
		c)
			echo "--------------------------";
			echo "| command toggle display |";
			echo "--------------------------";

			if [ "$cmd" = "comm," ]; then
				cmd="";
			else
				cmd="comm,";
			fi

			;;
		m)
			echo "-------------------------";
			echo "| memory toggle display |";
			echo "-------------------------";

			if [ "$mem" = "%mem," ]; then
				mem="";
			else
				mem="%mem,";
			fi

			;;
		p)
			echo "----------------------";
			echo "| cpu toggle display |";
			echo "----------------------";

			if [ "$cpu" = "%cpu" ]; then
				cpu="";
			else
				cpu="%cpu";
			fi

			;;
		u)
			echo "-------------------------";
			echo "| memory toggle display |";
			echo "-------------------------";

			if [ "$usr" = "user," ]; then
				usr="";
			else
				usr="user,";
			fi

			;;
		*)
			;;
	esac
done