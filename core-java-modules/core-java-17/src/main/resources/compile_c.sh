#!/bin/bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

gcc -c -fPIC $SCRIPTPATH/print_name.c
gcc -shared -rdynamic -o print_name.so print_name.o

mv print_name.so $SCRIPTPATH/
mv print_name.o $SCRIPTPATH/