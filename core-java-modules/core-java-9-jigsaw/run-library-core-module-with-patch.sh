#!/usr/bin/env bash
java --module-path mods:libs \
--add-modules com.baeldung.library.core \
--add-opens com.baeldung.library.core/com.baeldung.library.core=org.junit.platform.commons \
--add-reads com.baeldung.library.core=org.junit.jupiter.api \
--patch-module com.baeldung.library.core=outDir/library-test \
--module org.junit.platform.console --select-class com.baeldung.library.core.LibraryUnitTest