#!/usr/bin/env bash
cd src/main/java
java -XX:AOTLibrary=./jaotCompilation.so com/baeldung/java9/aot/JaotCompilation