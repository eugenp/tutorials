## Influx SDK Tutorial Project

### Relevant Article:
- [Introduction to Couchbase SDK for Java](http://www.baeldung.com/java-couchbase-sdk)

### Overview
This Maven project contains the Java code for the article linked above.

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
