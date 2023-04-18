#!/usr/bin/env bash
javac --class-path outDir/library-core/:\
libs/junit-jupiter-engine-5.9.2.jar:\
libs/junit-platform-engine-1.9.2.jar:\
libs/apiguardian-api-1.1.2.jar:\
libs/junit-jupiter-params-5.9.2.jar:\
libs/junit-jupiter-api-5.9.2.jar:\
libs/opentest4j-1.2.0.jar:\
libs/junit-platform-commons-1.9.2.jar \
-d outDir/library-test library-core/src/test/java/com/baeldung/library/core/LibraryUnitTest.java