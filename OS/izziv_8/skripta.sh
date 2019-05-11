#!/bin/bash

n=${1:-10};
m=${2:-10};

echo "Spawning $n processes!";
#for i in {1..$n};
#do
	#echo "i je $i";
	#if (( i == n )); then
	#	./subskripta.sh $m
	#else
	#	./subskripta.sh $m &
	#fi
#done
#wait;

for count in {0..29}; do
	sleep 100 &
	echo "$count"
done
wait
