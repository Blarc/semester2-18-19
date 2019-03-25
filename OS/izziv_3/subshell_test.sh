#!/bin/bash
# subshell_test.sh

(
while [ 1 ]
do
	echo "Subshell running . . . "
done
)

exit $?
