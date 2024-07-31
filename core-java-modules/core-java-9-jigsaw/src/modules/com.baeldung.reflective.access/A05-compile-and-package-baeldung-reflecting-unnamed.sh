#!/bin/bash

DIR=baeldung-reflecting-unnamed

# compile
mkdir -p out/${DIR}
javac -d out/${DIR} $(find ${DIR} -type f -name "*.java")

# package
mkdir -p mods
jar --create --file=mods/${DIR}.jar -C out/${DIR} .
