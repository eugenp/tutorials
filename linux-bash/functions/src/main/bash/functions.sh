#!/bin/bash


# simple input and output
# variable shadowing
# nesting and recursion


function simple_inputs() {
    echo "This is the first argument [$1]"
    echo "This is the second argument [$2]"
    echo "Calling function with $# aruments"
}

global_variable="lorem"
function simple_outputs() {
    sum=$(($1+$2)) 
    echo "Sum is $sum"
    global_variable="dolor"
    # this is just the status code
    # does not work if we want to return 
    # other than numerical 
    return $sum
}

# missing brackets still works
# as long as we have compound commands
function simple_for_loop()
    # echo "Looping through numbers"
    for ((i=0;i<5;++i)) do
        echo -n " "$i;
    done

function simple_comparison()
    if [[ "$1" -lt 5 ]]; then
        echo "$1 is smaller than 5"
    else
        echo "$1 is greater than 5"
    fi

# command groups with subshells 
# with the limitation of new enviornments
function simple_subshell()
    (
        echo "I'm a little tea pot"
        simple_comparison 10
        cd ..
        global_variable="ipsum"
    )

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



simple_inputs one two three
simple_outputs 1 2
simple_for_loop
simple_comparison 6
simple_comparison 4
simple_subshell
echo $global_variable

#echo $(fibonnaci_recursion 7)
