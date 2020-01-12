#!/bin/bash

# Subsection 2.1
simple_function() {
    for ((i=0;i<5;++i)) do
        echo -n " "$i" ";
    done
}

function simple_function_no_parantheses {
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
global_sum=0
function global_sum_outputs() {
    global_sum=$(($1+$2)) 
}

function cs_sum_outputs() {
    sum=$(($1+$2))
    echo $sum
}

# Subsection 2.4
function arg_ref_sum_outputs() {
    declare -n sum_ref=$3
    sum_ref=$(($1+$2)) 
}

# Subsection 3.1
variable="baeldung"
function variable_scope2(){
    echo "Variable inside function variable_scope2: [$variable]"
    local variable="ipsum"
}

function variable_scope(){
    local variable="lorem"
    echo "Variable inside function variable_scope: [$variable]"
    variable_scope2
}

# Subsection 3.2
subshell_sum=0
function simple_subshell_sum()
    (
        subshell_sum=$(($1+$2))
        echo "Value of sum in function with global variables: [$subshell_sum]"
    )

function simple_subshell_ref_sum() 
    (
        declare -n sum_ref=$3
        sum_ref=$(($1+$2))
        echo "Value of sum in function with ref arguments: [$sum_ref]"
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
            echo "User[${input[2]}]->File[${input[8]}]"
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

# main menu entry point
echo "****Functions samples menu*****"
PS3="Your choice (1,2,3 etc.):"
options=("function_definitions" "function_input_args" "function_outputs" \
         "function_variables" "function_subshells" "function_redirections" \
         "function_recursion" "quit")
select option in "${options[@]}"
do
    case $option in
        "function_definitions")
            echo -e "\n"
            echo "**Different ways to define a function**"
            echo -e "No function keyword:"
            simple_function
            echo -e "\nNo function parantheses:"
            simple_function_no_parantheses
            echo -e "\nOmitting curly braces:"
            simple_for_loop
            echo -e "\n"
            ;;
        "function_input_args")
            echo -e "\n"
            echo "**Passing inputs to a function**"
            simple_inputs lorem ipsum
            echo -e "\n"
            ;;
        "function_outputs")
            echo -e "\n"
            echo "**Getting outputs from a function**"
            global_sum_outputs 1 2
            echo -e ">1+2 using global variables: [$global_sum]"
            cs_sum=$(cs_sum_outputs 1 2)
            echo -e ">1+2 using command substitution: [$cs_sum]"
            arg_ref_sum_outputs 1 2 arg_ref_sum
            echo -e ">1+2 using argument references: [$arg_ref_sum]"
            echo -e "\n"
            ;;
        "function_variables")
            echo -e "\n"
            echo "**Overriding variable scopes**"
            echo "Global value of variable: [$variable]"
            variable_scope
            echo -e "\n"
            ;;
        "function_subshells")
            echo -e "\n"
            echo "**Running function in subshell**"
            echo "Global value of sum: [$subshell_sum]"
            simple_subshell_sum 1 2
            echo "Value of sum after subshell function with \
global variables: [$subshell_sum]"
            subshell_sum_arg_ref=0
            simple_subshell_ref_sum 1 2 subshell_sum_arg_ref
            echo "Value of sum after subshell function with \
ref arguments: [$subshell_sum_arg_ref]"
            echo -e "\n"
            ;;
        "function_redirections")
            echo -e "\n"
            echo "**Function redirections**"
            echo -e ">Function input redirection from file:"
            redirection_in
            echo -e ">Function input redirection from command:"
            redirection_in_ps
            echo -e ">Function output redirection to file:"
            redirection_out
            cat outfile
            echo -e ">Function output redirection to command:"
            red_ps=$(redirection_out_ps)
            echo "$red_ps"
            echo -e "\n"
            ;;
        "function_recursion")
            echo -e "\n"
            echo "**Function recursion**"
            fibo_res1=$(fibonnaci_recursion 7)
            echo "The 7th Fibonnaci number: [$fibo_res1]"
            fibo_res2=$(fibonnaci_recursion 15)
            echo "The 15th Fibonnaci number: [$fibo_res2]"
            echo -e "\n"
            ;;
        "quit")
            break
            ;;
        *) echo "Invalid option";;
    esac
done