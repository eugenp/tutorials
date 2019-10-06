## Couchbase SDK Tutorial Project

### Relevant Articles:
- [Introduction to Couchbase SDK for Java](https://www.baeldung.com/java-couchbase-sdk)
- [Using Couchbase in a Spring Application](https://www.baeldung.com/couchbase-sdk-spring)
- [Asynchronous Batch Operations in Couchbase](https://www.baeldung.com/async-batch-operations-in-couchbase)
- [Querying Couchbase with MapReduce Views](https://www.baeldung.com/couchbase-query-mapreduce-view)
- [Querying Couchbase with N1QL](https://www.baeldung.com/n1ql-couchbase)

### Overview
This Maven project contains the Java code for the Couchbase entities and Spring services
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
Java classes for the intro tutorial are in the
org.baeldung.couchbase.intro package.

Java classes for the Spring service tutorial are in the
org.baeldung.couchbase.spring package hierarchy.

Java classes for the Asynchronous Couchbase tutorial are in the
org.baeldung.couchbase.async package hierarchy.


### Running the tests
The test classes for the Spring service tutorial are:
- org.baeldung.couchbase.spring.service.ClusterServiceTest
- org.baeldung.couchbase.spring.person.PersonCrudServiceTest

The test classes for the Asynchronous Couchbase tutorial are in the
org.baeldung.couchbase.async package hierarchy:
- org.baeldung.couchbase.async.service.ClusterServiceTest
- org.baeldung.couchbase.async.person.PersonCrudServiceTest

The test classes may be run as JUnit tests from your IDE
or using the Maven command line:
```
mvn test
```
