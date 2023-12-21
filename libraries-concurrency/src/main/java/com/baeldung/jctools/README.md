## How to build and run the JMH benchmark

Execute the following from the project's root:  
```bash
mvn clean install
java -jar ./target/benchmarks.jar MpmcBenchmark -si true
```