#!/usr/bin/env bash
java -p libs \
org.junit.platform.console.ConsoleLauncher \
-cp ./outDir/library-core \
-c com.baeldung.library.core.LibraryUnitTest