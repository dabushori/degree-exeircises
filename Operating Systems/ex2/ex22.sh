#!/bin/bash
# Ori Dabush 212945760

# Script 2.1

# check that the numebr of arguments >= 4
if ! test $# -ge 4; then
    echo "Not enough parameters"
    exit 1
fi

# path to the ex21 script
ex21_path="$(pwd)/ex21.sh"

# a function that activates ex21 on the current directory and its subdirectories 
# and prints only the lines that contains not less words than the given number
function ex22() {
    # assign the given arguments to variables
    path_arg="." # instead of $1
    ending_arg=$2
    word_arg=$3
    max_len=$4

    # excecute ex21 script on the current directory and print only the lines that contains not less words than the given number
    bash "$ex21_path" $path_arg $ending_arg $word_arg | awk -v len=$max_len '{
        if (NF >= len) {
            print
        }
    }' 

    # get the files in the current directory
    new_line=$'\n'
    files="$(ls | sort -k 1,1 -t . )${new_line}"

    # call the ex22 recursively on every subdirectory
    for file in $files; do
        if test -d $file; then
            cd $file
            ex22 $file $ending_arg $word_arg $max_len
            cd ..
        fi
    done
}

# get into the given directory and call ex22 function
cd $1
ex22 "." $2 $3 $4