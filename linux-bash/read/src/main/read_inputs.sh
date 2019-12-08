#!/bin/bash

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

# section 2.2
custom_ifs_no_array(){
    IFS=";"
    read input1 input2 input3
    echo "[$input1] [$input2] [$input3]"
}

# section 2.1
default_read() {
    read input1 input2 input3
    echo "[$input1] [$input2] [$input3]"
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
advanced_pipeing(){
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


advanced_pipeing_ps(){
# process substitution
    declare -a input
    while read -a input
    do
        echo "${input[0]} ${input[8]}"
    done < <(ls -ll)
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
file_read
#prompt_read
#default_input_read
#advanced_pipeing
#custom_ifs_read
#infinite_read
# custom_ifs " "
# custom_ifs ";"
#custom_ifs_no_array







