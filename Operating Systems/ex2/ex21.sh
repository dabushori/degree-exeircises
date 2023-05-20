#!/bin/bash
# Ori Dabush 212945760

# Script 2.1

# check that the numebr of arguments >= 3
if ! test $# -ge 3; then
    echo "Not enough parameters"
    exit 1
fi

# assign the given arguments to variables
path_arg=$1
ending_arg=$2
word_arg=$3

# get in the given directory
cd $path_arg

# get the files in the directory sorted (without the extension)
new_line=$'\n'
files="$(ls | sort -k 1,1 -t . )${new_line}"

for f in $files; do
    # ignore the file if it is a directory
    if test -d $f; then
        continue
    fi

    # get the extension of the file 
    filename=$(basename -- "$f")
    extension="${filename##*.}"
    
    # check that the extension match the wanted extension
    if test $extension != $ending_arg; then
        continue
    fi
    
    # print the lines that contains the wanted word (incasesensitive)
    awk -v word=$word_arg '
    {
            line = $0
            gsub("[^a-zA-Z "word"]*", "")
            for (i = 1; i <= NF; ++i) {
                if (tolower(word) == tolower($i)) {
                    print line
                    break
                }
            }
        }' $f
done