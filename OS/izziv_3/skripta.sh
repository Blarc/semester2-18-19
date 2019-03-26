#!/bin/bash

if [ "$1" == "" ];
then
   echo "Podaj argumente, prijatelj!";
fi

n="$2";
n="${n:-Dober dan, Linux!}"
echo "$n";
