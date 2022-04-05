#!/usr/bin/env bash
cd src/main/java
javac com/baeldung/java9/aot/JaotCompilation.java
jaotc --output jaotCompilation.so com/baeldung/java9/aot/JaotCompilation.class

