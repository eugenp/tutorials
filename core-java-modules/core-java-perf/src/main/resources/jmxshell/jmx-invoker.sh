#!/bin/sh
jar='/tmp/core-java-perf.jar'
address='localhost:11234'
mbean='com.baeldung.jxmshell:name=calculator,type=basic'
operation='sum'

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
    ;;
    --get)
       shift
       operation="$1"
    ;;
    --set)
       shift
       operation="$1 $2"
    ;;
    -*)
      echo "bad option '$1'"
      exit 1
    ;;
    esac
    shift
done

java -cp "$jar" com.baeldung.jmxshell.custom.JmxInvoker \
  "service:jmx:rmi:///jndi/rmi://$address/jmxrmi" \
  "$mbean" \
  "$operation"
