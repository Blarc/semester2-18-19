#!/bin/bash

path=${1}"/*";

jpgc=0
pngc=0
gifc=0
for file in ${path}; do
  size=$(stat -c %s "${file}")
  if [[ ${file} == *".jpg" ]]; then
    (( jpgc += size ))
  elif [[ ${file} == *".png" ]]; then
    (( pngc += size ))
  elif [[ ${file} == *".gif" ]]; then
    (( gifc += size ))
  fi
done

printf "%s %d\n" "jpg" ${jpgc}
printf "%s %d\n" "png" ${pngc}
printf "%s %d\n" "gif" ${gifc}
