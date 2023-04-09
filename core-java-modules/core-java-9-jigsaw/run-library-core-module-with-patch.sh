#!/usr/bin/env bash
java -p mods:libs \
--add-modules com.baeldung.library.core \
--add-opens com.baeldung.library.core/com.baeldung.library.core=org.junit.platform.commons \
--add-reads com.baeldung.library.core=org.junit.jupiter.api \
--patch-module com.baeldung.library.core=outDir/library-test \
-m org.junit.platform.console -c com.baeldung.library.core.LibraryUnitTest