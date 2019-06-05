#!/bin/bash

path=$1
path="${path}*";

for file in ${path}; do
  if [[ ${file} = *"Abrakadabra"* ]]; then
    printf "%s\n" ${file}
  fi
done
