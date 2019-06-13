#!/bin/bash

path=${1:-"~"}
path="${path}/*";

function traverse {
  for file in ${path}; do
    if [[ ${file} = *"Abrakadabra"* ]]; then
      printf "%s\n" ${file}
    fi

    if [[ -d ${file} ]]; then
      traverse ${file}
    fi
  done
}

traverse ${path};
