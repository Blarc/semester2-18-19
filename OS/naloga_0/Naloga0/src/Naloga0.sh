#!/bin/bash

case "$1" in
	eho)
		echo "${@:2}";
		;;
	smisel)
		echo "Zivljenje je lepo, ce programiras."
		exit 42;
		;;
	macka)
		while read line
		do
			echo "$line"
		done
		;;
	*)
		echo "Podaj argumente prijatelj."
		exit 1;
		;;
esac

exit 0;
