#!/bin/bash

function fakrek() {
	if (( $1 <= 1 )); then
		echo 1;
	else
		temp=$(fakrek $(($1 - 1)));
		echo $(($1 * temp));
	fi
}

function fakiter() {
	i=1;
	res=1;
	while (( $i <= $1 )); do
		res=$(($res * $i));
		(( i++ ));
	done
	echo $res;
}

function fibrek() {
	if (( $1 <= 1 )); then
		echo 1;
	else
		temp=$(fibrek $(($1 - 1)));
		echo $(($1 + temp));
	fi
}

function fibiter() {
	res=0;
	i=$1;
	while (( i > 0 )); do
		(( res += i ));
		(( i-- ));
	done
	echo $res;
}

n="${2:-10}"

case "$1" in
	fakrek)
		fakrek $n;
		;;
	fakiter)
		fakiter $n;
		;;
	fibrek)
		fibrek $n;
		;;
	fibiter)
		fibiter $n;
		;;
	help)
		echo "fakrek - rekurziven izračun faktoriele"
		echo "fakiter - iterativen izračun faktoriele"
		echo "fibrek - rekurziven izračun Fibonaccijevega števila"
		echo "fibiter - iterativen izračun Fibonaccijevega števila"
		;;
	*)
		echo "Wrong parameters!"
		;;
esac

exit 0;