## Overview

This project holds a [couple of tests](./src/test/java/com/baeldung/jctools/JCToolsUnitTest.java) which illustrate JCTools specifics and a [benchmark](./src/main/java/com/baeldung/jctools/MpmcBenchmark.java) in the [JMH](http://openjdk.java.net/projects/code-tools/jmh/) format.  

## How to build and run the JMH benchmark

Execute the following from the project's root:  
```bash
mvn clean install
java -jar ./target/benchmarks.jar MpmcBenchmark -si true
```