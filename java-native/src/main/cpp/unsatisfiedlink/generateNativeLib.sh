#!/bin/bash
# Create the header with javac -h . ClassName.java
# Remember to set your JAVA_HOME env var
# Don't forget to set java.library.path to point to the folder where you have the lib you're loading.
MYSELF="$(readlink -f "$0")"
MYDIR="${MYSELF%/*}"

cd "$MYDIR"
class_name=com_baeldung_unsatisfiedlink_JniUnsatisfiedLink
lib_name=test

g++ -c -fPIC -I"${JAVA_HOME}/include" -I"${JAVA_HOME}/include/linux" ${class_name}.cpp -o ${class_name}.o
g++ -shared -fPIC -o lib${lib_name}.so ${class_name}.o -lc

# 32-bit version
g++ -m32 -c -fPIC -I"${JAVA_HOME}/include" -I"${JAVA_HOME}/include/linux" ${class_name}.cpp -o ${class_name}32.o
g++ -m32 -shared -fPIC -o lib${lib_name}32.so ${class_name}32.o -lc

# dummy version
touch ${class_name}-dummy.o

ls lib*
