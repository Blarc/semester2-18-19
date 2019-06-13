#!/bin/bash

path=${1:-"tocke.txt"}

while IFS=' ' read -ra line; do
    printf "%s " ${line[0]}
    if (( line[2] == "R" )); then
      line[2]=$(( (RANDOM % 5) + 1))
    fi
    sum=0
    for tocke in ${line[@]:1}; do
      (( sum += tocke ))
    done
    printf "%d\n" ${sum}
done <<< $(sort ${path})
