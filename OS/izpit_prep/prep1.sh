#!/bin/bash

function getppid {
  pid=${1};
  ppid=$(cut -d" " -f4 "/proc/${pid}/stat")
  echo ${ppid}
}

function getallpids {
  pid=${1};

  if (( pid == 1 )); then
    return;
  fi

  ppid=$(getppid ${pid})
  printf "%d " ${ppid}
  getallpids ${ppid}
}

pid=$1;
getallpids ${pid}
