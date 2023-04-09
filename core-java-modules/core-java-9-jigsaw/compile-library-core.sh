#!/usr/bin/env bash
javac --class-path /Users/yauhenikauko/.m2/repository/org/junit/jupiter/junit-jupiter-engine/5.9.2/junit-jupiter-engine-5.9.2.jar:\
/Users/yauhenikauko/.m2/repository/org/junit/platform/junit-platform-engine/1.9.2/junit-platform-engine-1.9.2.jar:\
/Users/yauhenikauko/.m2/repository/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar:\
/Users/yauhenikauko/.m2/repository/org/junit/jupiter/junit-jupiter-params/5.9.2/junit-jupiter-params-5.9.2.jar:\
/Users/yauhenikauko/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.9.2/junit-jupiter-api-5.9.2.jar:\
/Users/yauhenikauko/.m2/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar:\
/Users/yauhenikauko/.m2/repository/org/junit/platform/junit-platform-commons/1.9.2/junit-platform-commons-1.9.2.jar \
-d outDir/library-core library-core/src/main/java/com/baeldung/library/core/Book.java \
library-core/src/main/java/com/baeldung/library/core/Library.java \
library-core/src/main/java/com/baeldung/library/core/Main.java \
library-core/src/test/java/com/baeldung/library/core/LibraryUnitTest.java