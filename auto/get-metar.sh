#!/bin/bash

curl -s http://localhost:8080/subscriptions > /tmp/icao.json

jq ".[]" /tmp/icao.json | jq ".icaoCode" > /tmp/icaoCode.txt

sed -i 's/"//' /tmp/icaoCode.txt
sed -i 's/"//' /tmp/icaoCode.txt

icaoCodes="/tmp/icaoCode.txt"
while IFS= read -r icaocode
do
  #echo https://tgftp.nws.noaa.gov/data/observations/metar/stations/"$line".TXT
  curl -s https://tgftp.nws.noaa.gov/data/observations/metar/stations/"$icaocode".TXT > /tmp/metar.txt
  
  linenumber=1
  metars="/tmp/metar.txt"
  while IFS= read -r metar
  do
  	if [[ $linenumber -eq 2 ]]
  	then
   	  metarData="METAR ""$metar""="
   	  #echo "$metarData"
   	  curl -s -H "Content-Type: application/json" --request POST -d '{"data":"'"$metarData"'"}' http://localhost:8080/airport/"$icaocode"/METAR > /dev/null
   	fi
   	
   	linenumber=$(( $linenumber + 1 ))
  done < "$metars"

done < "$icaoCodes"

