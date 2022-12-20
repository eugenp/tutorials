#!/bin/bash

jar='/tmp/cmdline-jmxclient-0.10.3.jar'
address='localhost:11234'

mbean='com.baeldung.jxmshell:name=calculator,type=basic'
operation='sum'
command="$mbean"

while test $# -gt 0
do
    case "$1" in
    --jar)
        shift
        jar="$1"
    ;;
    --address)
        shift
        address="$1"
    ;;
    --mbean)
        shift
        mbean="$1"
    ;;
    --run|-x)
        shift
        operation="$1"

        command="${mbean} ${operation}"
    ;;
    --set)
        shift
        operation="$1"

        shift
        attribute_value="$1"
    
        command="${mbean} ${operation}=${attribute_value}"
    ;;
    --get)
        shift
        operation="$1"

        command="${mbean} ${operation}"
    ;;
    -*)
        echo "bad option '$1'"
        exit 1
    ;;
    esac
    shift
done

java -jar "$jar" - "$address" "$command"
