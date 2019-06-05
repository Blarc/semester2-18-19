#!/bin/bash

path="tocke.txt"
path=${1:-${path}}

while IFS=' ' read -ra line; do
  printf "%s" ${line[0]}
  if [[ ${line[2]} -eq 'R' ]]; then
    rnd=${RANDOM};
    sth=$(( (rnd % 5) + 1 ))
    line[2]=${sth}
  fi
  for points in ${line[@]:1}; do
    (( sum += points ))
  done;
  printf " %d\n" ${sum}
  (( sum = 0 ))
done < ${path}
