## Core Java JVM Cookbooks and Examples

This module contains articles about working with the Java Virtual Machine (JVM).

### Relevant Articles: 

- [Method Inlining in the JVM](https://www.baeldung.com/jvm-method-inlining)
- [JVM Log Forging](https://www.baeldung.com/jvm-log-forging)
- [Guide to Java Instrumentation](https://www.baeldung.com/java-instrumentation)
- [Class Loaders in Java](https://www.baeldung.com/java-classloaders)
- [A Guide to System.exit()](https://www.baeldung.com/java-system-exit)
- [Guide to System.gc()](https://www.baeldung.com/java-system-gc)
- [Runtime.getRuntime().halt() vs System.exit() in Java](https://www.baeldung.com/java-runtime-halt-vs-system-exit)
- [How to Get the Size of an Object in Java](http://www.baeldung.com/java-size-of-object)
- [What Causes java.lang.OutOfMemoryError: unable to create new native thread](https://www.baeldung.com/java-outofmemoryerror-unable-to-create-new-native-thread)
- [View Bytecode of a Class File in Java](https://www.baeldung.com/java-class-view-bytecode)
- More articles: [[next -->]](/core-java-modules/core-java-jvm-2)


To run the code for the Instrumentation: https://www.baeldung.com/java-instrumentation article:
1- build the module
2- run the module 3 times to build the 3 jars:
    mvn install -PbuildAgentLoader
    mvn install -PbuildApplication
    mvn install -PbuildAgent
3- update the commands in the article with the exact names of the jars generated in the target folder
4- update the path in the AgentLoader class with the path of the agent on your system 