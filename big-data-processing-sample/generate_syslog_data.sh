#!/bin/bash

LOGS_LINE_COUNT=$1

if [ -z "$LOGS_LINE_COUNT" ]; then
  echo "Error. Please enter line count."
  exit 1
fi

host="v.shipugin"
process="BARD_SIMPSON"

arr[0]="FRIDAYS ARE NOT «PANTS OPTIONAL»"
arr[1]="I DID NOT WIN THE NOBEL FART PRIZE"
arr[2]="I CAN’T SEE DEAD PEOPLE"
arr[3]="I WILL NOT SELL MY KIDNEY ON eBAY"
arr[4]="I WILL NOT CREATE ART FROM DUNG"
arr[5]="I WILL NOT DANCE ON ANYONE’S GRAVE"
arr[6]="I WILL NOT OBEY THE VOICES IN MY HEAD"
arr[7]="I WILL NOT PLANT SUBLIMINAL MESSAGORES"
arr[8]="I AM NOT THE ACTING PRESIDENT"
arr[9]="I WAS NOT THE SIXTH BEATLE"
arr[10]="I WILL ONLY PROVIDE A URINE SAMPLE WHEN ASKED"
arr[11]="I WILL NOT PUBLISH THE PRINCIPAL’S CREDIT REPORT"
arr[12]="THE HAMSTER DID NOT HAVE «A FULL LIFE»"
arr[13]="I SHOULD NOT BE TWENTY-ONE BY NOW"
arr[14]="IT’S «FACEBOOK», NOT «ASSBOOK»"

message_count=0

rand=$(($((RANDOM % 10)) % ${#arr[@]}))
message="${arr[$rand]}"

function generate_logs_line() {
  dateTime=$(gdate -d "$((RANDOM % 1 + 2020))-$((RANDOM % 12 + 1))-$((RANDOM % 28 + 1)) $((RANDOM % 23 + 1)):$((RANDOM % 59 + 1)):$((RANDOM % 59 + 1))" '+%Y-%m-%dT%H:%M:%S')

  priority=
  case $((RANDOM % 8)) in
  0) priority="EMERGENCY" ;;
  1) priority="ALERT" ;;
  2) priority="CRITICAL" ;;
  3) priority="ERROR" ;;
  4) priority="WARNING" ;;
  5) priority="NOTICE" ;;
  6) priority="INFORMATIONAL" ;;
  7) priority="DEBUG" ;;
  esac

  if [[ "$message_count" -lt $((LOGS_LINE_COUNT / 10)) ]]; then
    message_count=$((message_count + 1))
  else
    rand=$(($((RANDOM % 10)) % ${#arr[@]}))
    message="${arr[$rand]}"
    message_count=0
  fi

  if [ $priority ]; then
    echo "$dateTime" "$host" "$process": \<$priority\> "$message"
  else
    echo "$dateTime" "$host" "$process": "$message"
  fi
}

for i in $(seq "$LOGS_LINE_COUNT"); do
  generate_logs_line
done
