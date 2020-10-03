#!/bin/bash
set -e

# This script generates a random set of words.
# usage: ./random-word-generator.sh -c=10 -o="./inputFiles/input_files.txt"

function translit() {
  WORD=$1

  # sed-ом заменяем символы латиницы на символы кирилицы
  TRS=$(echo "$WORD" | sed "y/abvgdeeziilmnoprufh/абвгдеёзийлмнопруфх/")
  TRS=$(echo "$TRS" | sed "y/ABVGDEEZIILMNOPRUFH/АБВГДЕЁЗИЙЛМНОПРУФХ/")

  TRS=${TRS//оо/у}
  TRS=${TRS//zh/ж}
  TRS=${TRS//ch/ч}
  TRS=${TRS//sh/ш}
  TRS=${TRS//sch/щ}
  TRS=${TRS//yu/ю}
  TRS=${TRS//ya/я}
  TRS=${TRS//tc/ц}
  TRS=${TRS//s/с}
  TRS=${TRS//k/к}
  TRS=${TRS//K/к}
  TRS=${TRS//кх/х}
  TRS=${TRS//e/э}
  TRS=${TRS//t/т}

  echo "$TRS"
}

# Parsing input params
for i in "$@"; do
  case $i in
  -c=* | --count=*)
    COUNT="${i#*=}"
    ;;
  -o=* | --output_file=*)
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

# Getting words from standard dictionary
words=($(cat "/usr/share/dict/words" | sort -R | head -n "$COUNT" | xargs -0))

word_with_error=0

# Translitting random words to cyrillic
for ((i = 0; i < ${#words[@]}; i++)); do
  if (($(((RANDOM % 2) == 1)))); then
    words[$i]=$(translit "${words[$i]}")

    word_with_error=$((word_with_error + 1))
  fi
done

echo "${words[@]}" >"$OUTPUT_FILE"
echo "Count of cyrillic words: $word_with_error"
