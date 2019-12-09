#!/bin/bash

# section 2.1
default_read() {
    read input1 input2 input3
    echo "[$input1] [$input2] [$input3]"
}

# section 2.2
custom_ifs_no_array(){
    IFS=";"
    read input1 input2 input3
    echo "[$input1] [$input2] [$input3]"
}

# Section 2.3
prompt_read_password(){
    prompt="You shall not pass:"
    read -p "$prompt" -s input
    echo -e "\ninput password [$input]"
}

array_read() {
    declare -a input_array
    text="baeldung is a cool tech site"
    read -e -i "$text" -a input_array 
    for input in ${input_array[@]} 
        do
            echo " word [$input]"
        done
}

# section 3.1
file_read(){
    exec {file_descriptor}<"./file.csv"
    declare -a input_array
    delimiter=";"
    while IFS="," read -a input_array -d $delimiter -u $file_descriptor 
        do
            echo "${input_array[0]},${input_array[2]}" 
        done
    exec {file_descriptor}>&-
}

# section 3.2
command_pipe(){
    ls -ll | { declare -a input
               read
               while read -a input; 
               do
                   echo "${input[0]} ${input[8]}"
               done }
}

# section 3.3
timeout_input_read(){
    prompt="You shall not pass:"
    read -p "$prompt" -s -r -t 5 input
        if [ -z "$input" ]; then
            echo -e "\ntimeout occured!"
        else
            echo -e "\ninput word [$input]"
        fi
}

exactly_n_read(){
    prompt="Reading exactly 11 chars:"
    read -p "$prompt" -N 11 -t 5 input1 input2 
    echo -e "\ninput word1 [$input1]"
    echo "input word2 [$input2]"
}