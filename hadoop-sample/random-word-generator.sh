#!/bin/bash
set -e

# This script generates a random set of words.
# usage: ./random-word-generator.sh -c=10 -o="./inputFiles/input_files.txt"

for i in "$@"
do
case $i in
    -c=*|--count=*)
      COUNT="${i#*=}"
    ;;
    -o=*|--output_file=*)
      OUTPUT_FILE="${i#*=}"
    ;;
    *)
      echo "Unknown parameter $i"
      exit 1
    ;;
esac
done

if [ -z "$COUNT" ]; then
    echo "Error. Please enter word count -c=[word_count]."
    exit 1
fi

if [ -z "$OUTPUT_FILE" ]; then
    echo "Error. Please enter output file -o=[output_file]."
    exit 1
fi
echo "COUNT=$COUNT"
echo "OUTPUT_FILE=$OUTPUT_FILE"

cat "/usr/share/dict/words"|sort -R|head -n "$COUNT"|xargs > "$OUTPUT_FILE"