## Core Java JVM Cookbooks and Examples

To run the code for the Instrumentation: https://www.baeldung.com/java-instrumentation article:
1- build the module
2- run the module 3 times to build the 3 jars:
    mvn install -PbuildAgentLoader
    mvn install -PbuildApplication
    mvn install -PbuildAgent
3- update the commands in the article with the exact names of the jars generated in the target folder
4- update the path in the AgentLoader class with the path of the agent on your system 