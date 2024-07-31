#!/usr/bin/env bash
javac --class-path libs/junit-jupiter-engine-5.9.2.jar:\
libs/junit-platform-engine-1.9.2.jar:\
libs/apiguardian-api-1.1.2.jar:\
libs/junit-jupiter-params-5.9.2.jar:\
libs/junit-jupiter-api-5.9.2.jar:\
libs/opentest4j-1.2.0.jar:\
libs/junit-platform-commons-1.9.2.jar \
-d outDir/library-core \
library-core/src/main/java/com/baeldung/library/core/Book.java \
library-core/src/main/java/com/baeldung/library/core/Library.java \
library-core/src/main/java/com/baeldung/library/core/Main.java