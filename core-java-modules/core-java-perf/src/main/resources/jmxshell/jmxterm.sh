#!/bin/sh

jar='/tmp/jmxterm-1.0.4-uber.jar'
address='localhost:11234'

mbean='com.baeldung.jxmshell:name=calculator,type=basic'
operation='sum'
command="info -b $mbean"

while test $# -gt 0
do
    case "$1" in
    --jar|-j)
        shift; jar="$1"
    ;;
    --address|-l)
        shift; address="$1"
    ;;
    --mbean|-b)
        shift; mbean="$1"
    ;;
    --run|-x)
        shift; operation="$1"

        command="run -b ${mbean} ${operation}"
    ;;
    --set)
        shift; operation="$1"
        shift; attribute_value="$1"
    
        command="set -b ${mbean} ${operation} ${attribute_value}"
    ;;
    --get)
        shift; operation="$1"

        command="get -b ${mbean} ${operation}"
    ;;
    -*)
        echo "bad option '$1'"
        exit 1
    ;;
    esac
    shift
done

echo "$command" | java -jar "$jar" -l "$address" -n -v silent
