#!/usr/bin/env bash
javac -d outDir --module-source-path src/simple-modules $(find src/simple-modules -name "*.java")
