#!/usr/bin/env bash
javac --module-path mods:libs -d mods/com.baeldung.library.test $(find library-test/src/test -name "*.java")