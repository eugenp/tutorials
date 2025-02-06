## Core Java JVM Cookbooks and Examples

This module contains articles about working with the Java Virtual Machine (JVM).

### Relevant Articles: 

- [Guide to Java Instrumentation](https://www.baeldung.com/java-instrumentation)
- [Class Loaders in Java](https://www.baeldung.com/java-classloaders)
- [A Guide to System.exit()](https://www.baeldung.com/java-system-exit)
- [How to Get the Size of an Object in Java](http://www.baeldung.com/java-size-of-object)
- [Measuring Object Sizes in the JVM](https://www.baeldung.com/jvm-measuring-object-sizes)
- [Adding Shutdown Hooks for JVM Applications](https://www.baeldung.com/jvm-shutdown-hooks)
- [Difference Between Class.getResource() and ClassLoader.getResource()](https://www.baeldung.com/java-class-vs-classloader-getresource)
- More articles: [[next -->]](/core-java-modules/core-java-jvm-2)


To run the code for the Instrumentation: https://www.baeldung.com/java-instrumentation article:
1- build the module
2- run the module 3 times to build the 3 jars:
    mvn install -PbuildAgentLoader
    mvn install -PbuildApplication
    mvn install -PbuildAgent
3- update the commands in the article with the exact names of the jars generated in the target folder
4- update the path in the AgentLoader class with the path of the agent on your system 