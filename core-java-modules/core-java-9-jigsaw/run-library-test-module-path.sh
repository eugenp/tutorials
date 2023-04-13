#!/usr/bin/env bash
java --module-path mods:libs \
--add-modules com.baeldung.library.test \
--add-opens com.baeldung.library.core/com.baeldung.library.core=com.baeldung.library.test \
org.junit.platform.console.ConsoleLauncher --select-class com.baeldung.library.test.LibraryUnitTest