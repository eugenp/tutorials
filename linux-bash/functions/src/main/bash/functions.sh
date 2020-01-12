#!/bin/bash

# Subsection 2.1
simple_function() {
    echo "First"
    for ((i=0;i<5;++i)) do
        echo -n " "$i" ";
    done
}

function simple_function {
    echo "Second"
    for ((i=0;i<5;++i)) do
        echo -n " "$i" ";
    done
}

function simple_for_loop()
    for ((i=0;i<5;++i)) do
        echo -n " "$i" ";
    done

function simple_comparison()
    if [[ "$1" -lt 5 ]]; then
        echo "$1 is smaller than 5"
    else
        echo "$1 is greater than 5"
    fi

# Subsection 2.2
function simple_inputs() {
    echo "This is the first argument [$1]"
    echo "This is the second argument [$2]"
    echo "Calling function with $# aruments"
}

# Subsection 2.3
sum=0
function simple_outputs() {
    sum=$(($1+$2)) 
}

function simple_outputs() {
    sum=$(($1+$2)) 
    echo $sum
}

# Subsection 2.4
function ref_outputs() {
    declare -n sum_ref=$3
    sum_ref=$(($1+$2)) 
}

# Subsection 3.1
variable="baeldung"
function variable_scope2(){
    echo "Variable inside function variable_scope2 : [$variable]"
    local variable="ipsum"
}

function variable_scope(){
    local variable="lorem"
    echo "Variable inside function variable_scope : [$variable]"
    variable_scope2
}

# Subsection 3.2
sum=0
function simple_subshell()
    (
        sum=$(($1+$2))
    )

function simple_subshell_ref() 
    (
        declare -n sum_ref=$3
        sum_ref=$(($1+$2))
    )

# Subsection 3.3
function redirection_in() {
    while read input;
        do
            echo "$input"
        done
} < infile

function redirection_in_ps() {
    read
    while read -a input;
        do
            echo "${input[2]} ${input[8]}"
        done
} < <(ls -ll /)

function redirection_out_ps(){
    declare -a output=("baeldung" "lorem" "ipsum" "caracg")
    for element in "${output[@]}"
        do
            echo "$element"
        done
} > >(grep "g")

function redirection_out() {
    declare -a output=("baeldung" "lorem" "ipsum")
    for element in "${output[@]}"
        do
            echo "$element"
        done
} > outfile

# Subsection 3.4
function fibonnaci_recursion() {
    argument=$1
    if [[ "$argument" -eq 0 ]] || [[ "$argument" -eq 1 ]]; then
        echo $argument
    else
        first=$(fibonnaci_recursion $(($argument-1)))
        second=$(fibonnaci_recursion $(($argument-2)))
        echo $(( $first + $second ))
    fi 
}

#simple_function
# simple_inputs one 'two three'
# sum=$(simple_outputs 1 2)
# echo "Sum is $sum"
# sum=0
# ref_outputs 1 9 sumt
# echo "Sum is $sumt"
# simple_for_loop
# simple_comparison 6
# simple_comparison 4
# simple_subshell 1 2 sum
# echo "Sum is $sum"

#variable_scope
# echo "Variable outside function variable_scope : [$variable]"
# FUNCNEST=5
# echo $(fibonnaci_recursion 7)
# echo $(fibonnaci_recursion 15)
# redirection_in 
# redirection_in_ps
# redirection_out
# redirection_out_ps
