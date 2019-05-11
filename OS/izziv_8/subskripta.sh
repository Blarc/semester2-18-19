#!/bin/bash

m=${1:-100};
for (( i = 0; i <= m; i++ )); do
	echo $i
done
