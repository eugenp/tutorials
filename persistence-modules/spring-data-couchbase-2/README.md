## Spring Data Couchbase Tutorial Project

### Relevant Articles:
- [Intro to Spring Data Couchbase](https://www.baeldung.com/spring-data-couchbase)
- [Entity Validation, Optimistic Locking, and Query Consistency in Spring Data Couchbase](https://www.baeldung.com/entity-validation-locking-and-query-consistency-in-spring-data-couchbase)
- [Multiple Buckets and Spatial View Queries in Spring Data Couchbase](https://www.baeldung.com/spring-data-couchbase-buckets-and-spatial-view-queries)

### Overview
This Maven project contains the Java code for Spring Data Couchbase
entities, repositories, and template-based services
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

### Package Organization
Java classes for the first two tutorials listed above are in src/main/java in the package hierarchy
com.baeldung.spring.data.couchbase

Java classes for the multiple-bucket tutorials are in src/main/java in the package hierarchy
com.baeldung.spring.data.couchbase2b

### Running the tests
The test classes for the single-bucket tutorials are in src/test/java in the package
com.baeldung.spring.data.couchbase.service:
- PersonServiceTest (abstract)
- PersonRepositoryTest (concrete)
- PersonTemplateServiceTest (concrete)
- StudentServiceTest (abstract)
- StudentRepositoryTest (concrete)
- StudentTemplateServiceTest (concrete)

The concrete test classes for the multiple-bucket tutorial are in src/test/java in the package
com.baeldung.spring.data.couchbase2b.service:
- CampusRepositoryServiceImplTest
- PersonRepositoryServiceImplTest
- StudentRepositoryServiceImplTest

The concrete test classes may be run as JUnit tests from your IDE
or using the Maven command line:
```
mvn test
```
