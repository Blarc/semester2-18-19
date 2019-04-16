#!/bin/bash

status() {
	a=$1
	b=$2

	if (( a < b )); then
		c=$a;
	else
		c=$b;
	fi

	for (( i = 1; i <= c; i++ )); do
		if (( a % i == 0  &&  b % i == 0 )); then
			gcd=$i;
		fi
	done

	exit $gcd;

}

leto() {
	for i in "${@:2}"
	do
		if (( i % 4 == 0 && (i % 100 != 0 || i % 400 == 0) )); then
			echo "Leto $i je prestopno.";
		else
			echo "Leto $i ni prestopno.";
		fi
	done
}

fibiter() {
	res=0;
	for ((i = 1, b = 0; b < $1; i = res - i, b++)); do
		res=$(($res + $i))
	done
	echo $res;
}

fib() {
	for i in "${@:2}"
	do
		tmp=$(fibiter $i)
		echo "$i: $tmp";
	done
}

userinfo() {
	for i in "${@:2}"
	do
		if !(id "$i" >/dev/null 2>&1); then
			echo "$i: err";
			break;
		fi

		uid=$(id -u $i);
		gid=$(id -g $i);
		grp=$(id -G $i | wc -w);
		dir1="/home/$i";
		dir2="/home/uni/$i"

		if [ -d "$dir1" ] || [ -d "$dir2" ]; then
			exist=true;
		else
			exist=false;
		fi

		if (( uid == gid )); then
			if [ "$exist" = true ]; then
				echo "$i: enaka obstaja $grp";
			else
				echo "$i: enaka $grp";
			fi
		else
			if [ "$exist" = true ]; then
				echo "$i: enaka $grp"
			else
				echo "$i: $grp";
			fi
		fi

	done

}

tocke() {
	# rabi skok v novo vrstico na koncu
	n=0;
	sumAll=0;
	while read line
	do
		lineArray=($line);
		if [ ${lineArray[0]} != "#" ]; then
			sum=$(( lineArray[1] + lineArray[2] + lineArray[3] ));
			tip=${lineArray[4]};
			tmp=${lineArray[0]:2:2};
			# echo "Tmp: $tmp";
			# echo "Sum: $sum";
			# echo "Tip: $tip";
			if [ "$tip" == "P" ] || [ "$tip" == "p" ]; then
				# echo "I'm in!";
				(( sum /= 2 ))
			elif [ "$tmp" == 14 ]; then
				RANDOM=42;
				rnd=$(( ( RANDOM % 5 ) + 1 ));
				# echo "Rnd: $rnd";
				(( sum += rnd ));
			fi

			if (( sum > 50 )); then
				(( sum = 50 ));
			fi

			echo "${lineArray[0]}: $sum";

			(( n += 1 ))
			(( sumAll += sum ))
		fi
	done

	echo "St. studentov: $n";
	echo "Povprecne tocke: $(( sumAll / n ))";
}

drevo() {
	# idk how to get types
	dir=${1:-$(pwd)};
	depth=${2:-3};

	# ce je empty ne dela
	for i in $dir/*
	do
		type=$(file -b $i);
		echo "$type ${i##*/}";
		if [ "$type" == "directory" ]; then
			echo "next dir: $i";
			drevo $i $(( depth-1 ))
		fi 
	done
}

case "$1" in
	pomoc)
		echo "Uporaba: ./Naloga1.sh akcija parametri";
		exit 0;
		;;
	status)
		status $2 $3;
		;;
	leto)
		leto "$@";
		;;
	fib)
		fib "$@";
		;;
	userinfo)
		userinfo "$@";
		;;
	tocke)
		tocke;
		;;
	drevo)
		drevo $2 $3;
		;;
	prostor)
		#prostor "$@";
		echo "Not implemented yet!";
		;;
	*)
		echo "Napacna uporaba skripte!";
		echo "Uporaba: ./Naloga.sh akcija parametri";
		
		
esac
