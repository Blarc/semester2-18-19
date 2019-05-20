#!/bin/bash

header="https://openweathermap.org/data/2.5/weather?q="
ending=",uk&appid=727f17bc0879568fd7b7684607ebbf94"

city="$1"

api=$header$city$ending

temp=$(curl -s "$api" | jq -r ".main.temp");

echo "$temp"

"https://openweathermap.org/data/2.5/weather?q=London&appid=727f17bc0879568fd7b7684607ebbf94"