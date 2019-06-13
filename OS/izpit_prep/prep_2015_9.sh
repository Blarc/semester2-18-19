#!/bin/bash

one=${1:-"kvizi.txt"}
two=${2:-"izzivi.txt"}

one_sorted=$(sort "${one}")
two_sorted=$(sort "${two}")

IFS=", "
while read -ra line_one; do
  vpisna=${line_one[0]}
  kvizi_tocke=${line_one[1]}

  izzivi_tocke=$(grep ${vpisna} ${two} | tr -d "," | cut -d ' ' -f12)

  printf "%d %d %d\n" ${vpisna} ${kvizi_tocke} ${izzivi_tocke}

done <<< "${one_sorted}"
