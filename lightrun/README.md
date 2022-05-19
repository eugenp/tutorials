# Lightrun Example Application - Tasks Management

This application exists as an example for the Lightrun series of articles.

## Building
This application requires [Apache Maven](https://maven.apache.org/) and [Java 17+](https://www.oracle.com/java/technologies/downloads/).

Building the code is done by executing:
```
$ mvn install
```
from the top level.

## Running
The application consists of three services:
* Tasks
* Users
* API

These are all Spring Boot applications.

The Tasks and Users services exist as microservices for managing one facet of data. Each uses a database, and utilise a JMS queue between them as well. For convenience this infrastructure is all embedded in the applications.

This does mean that the startup order is important. The JMS queue exists within the Tasks service and is connected to from the Users service. As such, the Tasks service must be started before the others.

Each service can be started either by executing `mvn spring-boot:run` from within the appropriate directory. Alternatively, as Spring Boot applications, the build will produce an executable JAR file within the `target` directory that can be executed as, for example:
```
$ java -jar ./target/tasks-service-0.0.1-SNAPSHOT.jar
```
