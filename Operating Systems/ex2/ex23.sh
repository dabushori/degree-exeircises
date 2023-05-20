#!/bin/bash
# Ori Dabush 212945760

# files names
OS_RELEASE_FILE="os-release"
HOSTNAMECTL_FILE="hostnamectl"

# identify if it is system or host
SYSTEM=0
HOST=0

# check that there are any arguments
if ! test $# -ge 1; then
    echo "Invalid input"
    exit 1
fi

# check that the first argument is system or host
if test $1 == "system"; then
    SYSTEM=1
elif test $1 == "host"; then
    HOST=1
else
    echo "Invalid input"
    exit 1
fi
shift

# check that it is system or host
if [[ $HOST -eq 0 && $SYSTEM -eq 0 ]]; then
    echo "Invalid input"
    exit 1
fi

# get the arguments (without duplicates)
args=$(echo "$@" | awk '{for (i=1;i<=NF;i++) if (!a[$i]++) printf("%s%s",$i,FS)}{printf("\n")}')
# print the entire file if there are no flags
if [ -z "$args" ]; then
    if test $SYSTEM -eq 1; then
        cat os-release
    else
        cat hostnamectl
    fi
    exit 0
fi

has_correct_flag=0

# print every flag info (and set has_correct_flag to 1 if there is at least one correct flag)
if test $SYSTEM -eq 1; then
    for arg in $args; do
        field_name=$(echo "$arg" | sed 's/--//')
        case $arg in 
            --name)
            awk -F "=" 'tolower($1) == "name" { gsub("[\"]", ""); print $2 }' os-release
            has_correct_flag=1
            ;;

            --version)
            awk -F "=" 'tolower($1) == "version" { gsub("[\"]", ""); print $2 }' os-release
            has_correct_flag=1
            ;;

            --pretty_name)
            awk -F "=" 'tolower($1) == "pretty_name" { gsub("[\"]", ""); print $2 }' os-release
            has_correct_flag=1
            ;;

            --home_url)
            awk -F "=" 'tolower($1) == "home_url" { gsub("[\"]", ""); print $2 }' os-release
            has_correct_flag=1
            ;;

            --support_url)
            awk -F "=" 'tolower($1) == "support_url" { gsub("[\"]", ""); print $2 }' os-release
            has_correct_flag=1
            ;;
        esac
    done
fi
    

if test $HOST -eq 1; then
    for arg in $args; do
        case $arg in 
            --icon_name)
            awk -F ": " ' { 
                gsub("[\"]", ""); 
                gsub(/^[ \t]+|[ \t]+$/, "");
                gsub("[ ]+", " "); 
                if (tolower($1) == "icon name") {
                    print $2
                }
            }' hostnamectl
            has_correct_flag=1
            ;;

            --static_hostname)
            awk -F ": " ' { 
                gsub("[\"]", ""); 
                gsub(/^[ \t]+|[ \t]+$/, "");
                gsub("[ ]+", " "); 
                if (tolower($1) == "static hostname") {
                    print $2
                }
            }' hostnamectl
            has_correct_flag=1
            ;;

            --machine_id)
            awk -F ": " ' { 
                gsub("[\"]", ""); 
                gsub(/^[ \t]+|[ \t]+$/, "");
                gsub("[ ]+", " "); 
                if (tolower($1) == "machine id") {
                    print $2
                }
            }' hostnamectl
            has_correct_flag=1
            ;;

            --boot_id)
            awk -F ": " ' { 
                gsub("[\"]", ""); 
                gsub(/^[ \t]+|[ \t]+$/, "");
                gsub("[ ]+", " "); 
                if (tolower($1) == "boot id") {
                    print $2
                }
            }' hostnamectl
            has_correct_flag=1
            ;;

            --virtualization)
            awk -F ": " ' { 
                gsub("[\"]", ""); 
                gsub(/^[ \t]+|[ \t]+$/, "");
                gsub("[ ]+", " "); 
                if (tolower($1) == "virtualization") {
                    print $2
                }
            }' hostnamectl
            has_correct_flag=1
            ;;

            --kernel)
            awk -F ": " ' { 
                gsub("[\"]", ""); 
                gsub(/^[ \t]+|[ \t]+$/, "");
                gsub("[ ]+", " "); 
                if (tolower($1) == "kernel") {
                    print $2
                }
            }' hostnamectl
            has_correct_flag=1
            ;;

            --architecture)
            awk -F ": " ' { 
                gsub("[\"]", ""); 
                gsub(/^[ \t]+|[ \t]+$/, "");
                gsub("[ ]+", " "); 
                if (tolower($1) == "architecture") {
                    print $2
                }
            }' hostnamectl
            has_correct_flag=1
            ;;
        esac
    done
fi

#if there were not at least one correct flag, print the entire file
if test $has_correct_flag -eq 0; then
    if test $SYSTEM -eq 1; then
        cat os-release
    else
        cat hostnamectl
    fi
fi