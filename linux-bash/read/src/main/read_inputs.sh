#!/bin/bash


default_read() {
    echo "Please enter something:"
    read first second third
    echo "first word [$first]"
    echo "second word [$second]"
    echo "third word [$third]"
}

array_read() {
    declare -a input_array
    echo "Please enter something:"
    read -a input_array
    for input in ${input_array[@]} 
        do
            echo " Word [$input]"
        done
}

special_delim() {
    echo "Please enter something:"
    read -d ";" first second third
    echo "first word [$first]"
}

file_read(){
    # open file descriptor for reading
    exec {file_descriptor}<"./dummy_file"
    declare -a input_array
    echo "Reading first line from file"
    read -a input_array -u $file_descriptor 
    for input in ${input_array[@]} 
        do
            echo " Word [$input]"
        done
    # close file descriptor after reading
    exec {file_descriptor}>&-
}

prompt_read(){
    echo "With prompt :"
    prompt="You shall not pass:"
    read -p "$prompt" input
    echo "word -> [$input]"
}

default_input_read(){
    echo "Default input read:"
    prompt=$'What\'s up doc: \n'
    default_input="Nothing much just killing time"
    read -e -p "$prompt" -i "$default_input" actual_input
    echo "word -> [$actual_input]"
}

advanced_pipeing(){
    ls | (read -p "Input from ls" input; echo "Single read -> [$input]")
    ls | (while IFS= read input; 
            do
                echo "$input"
            done )
    # process substitution
    while read input
    do
        echo "$input"
    done < <(ls)

}

custom_ifs_read(){
    declare -a input_array
    while IFS=";" read -a input_array -r
        do
            for element in ${input_array[@]}
                do
                    echo -n "$element.backup "
                done
            echo ""
        done < "file.csv"
}

infinite_read(){
    while read -d "?" -ep "Input:" input
        do
            echo "$input"
        done
}

custom_ifs(){
    declare -a input_array
    IFS=$1 # whitespace 
    read -a input_array -p "Enter something with delimiter <$1>:"
    for input in ${input_array[@]}
        do
            echo "[$input]"
        done
}

#default_read
#array_read
#special_delim
#file_read
#prompt_read
#default_input_read
#advanced_pipeing
#custom_ifs_read
#infinite_read
custom_ifs " "
custom_ifs ";"







