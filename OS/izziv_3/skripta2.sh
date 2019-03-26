#!/bin/bash

for var in "${@:3}"
do
   if [ "$var" -eq "$2" ]
   then
   echo "$var";
   fi
done
