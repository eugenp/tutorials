#!/usr/bin/env bash
javac -p mods:libs \
-d mods/com.baeldung.library.test $(find library-test/src/test -name "*.java")