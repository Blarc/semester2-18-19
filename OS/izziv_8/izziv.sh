#!/bin/bash

n=${1:-100};

echo "Spawning $n processes!";
children_pids=()

for (( i = 0; i < n; i++ )); do
	xeyes &
	children_pids+=("$!");
done

echo "PIDs: ${children_pids[*]}"

read -n 1 -s;

echo "Killing pocesses!";

printf "%s" "KILLs: "
for (( i = ${#children_pids[@]} - 1; i >= 0; i-- )); do
	kill "${children_pids[i]}" 2>/dev/null
	tmp=$?
	wait "${children_pids[i]}" 2>/dev/null

	if [ $tmp != 1 ]; then
		printf "%d " "${children_pids[i]}";
	fi
done

echo
echo "Šokantno!"
echo "Processes killed."

wait
exit 0;

