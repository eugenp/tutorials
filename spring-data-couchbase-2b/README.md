## Spring Data Couchbase Tutorial Project

### Relevant Articles:
- [Spring Data Couchbase](http://www.baeldung.com/spring-data-couchbase)
- [Entity Validation, Query Consistency, and Optimistic Locking in Spring Data Couchbase](http://www.baeldung.com/entity-validation-locking-and-query-consistency-in-spring-data-couchbase)

### Overview
This Maven project contains the Java code for Spring Data Couchbase
entities, repositories, and repository-based services
as described in the tutorials, as well as a unit/integration test
for each service implementation.

### Working with the Code
The project was developed and tested using Java 7 and 8 in the Eclipse-based
Spring Source Toolkit (STS) and therefore should run fine in any
recent version of Eclipse or another IDE of your choice
that supports Java 7 or later.

### Building the Project
You can also build the project using Maven outside of any IDE:
```
mvn clean install
```

### Running the tests
The following test classes are in src/test/java in the package
org.baeldung.spring.data.couchbase.service:
- CampusRepositoryServiceTest
- PersonRepositoryServiceTest
- StudentRepositoryServiceTest

These may be run as JUnit tests from your IDE
or using the Maven command line:
```
mvn test
```
