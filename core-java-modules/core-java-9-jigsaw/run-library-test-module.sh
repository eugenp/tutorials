#!/usr/bin/env bash
java -p mods:libs \
--add-modules com.baeldung.library.test \
--add-opens com.baeldung.library.core/com.baeldung.library.core=com.baeldung.library.test \
org.junit.platform.console.ConsoleLauncher -c com.baeldung.library.test.LibraryUnitTest