#!/bin/bash

DIR=baeldung-reflecting-named

# compile
mkdir -p out/${DIR}
javac -d out/${DIR} --module-path mods $(find ${DIR} -type f -name "*.java")

# package
mkdir -p mods
jar --create --file=mods/${DIR}.jar -C out/${DIR} .
