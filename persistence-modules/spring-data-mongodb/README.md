=========

## Spring Data MongoDB


### Relevant Articles: 
- [A Guide to Queries in Spring Data MongoDB](http://www.baeldung.com/queries-in-spring-data-mongodb)
- [Spring Data MongoDB – Indexes, Annotations and Converters](http://www.baeldung.com/spring-data-mongodb-index-annotations-converter)
- [Custom Cascading in Spring Data MongoDB](http://www.baeldung.com/cascading-with-dbref-and-lifecycle-events-in-spring-data-mongodb)
- [Introduction to Spring Data MongoDB](http://www.baeldung.com/spring-data-mongodb-tutorial)
- [Spring Data MongoDB: Projections and Aggregations](http://www.baeldung.com/spring-data-mongodb-projections-aggregations)
- [Spring Data MongoDB Transactions](https://www.baeldung.com/spring-data-mongodb-transactions )
- [ZonedDateTime with Spring Data MongoDB](https://www.baeldung.com/spring-data-mongodb-zoneddatetime)


## Spring Data MongoDB Live Testing


There are 3 scripts to simplify running live tests:
1. [`live-test-setup.sh`](src/live-test/resources/live-test-setup.sh) builds a docker image with the necessary setup and runs it. The environment is ready, when the log stops - it takes approximately 30 seconds.
2. [`live-test.sh`](src/live-test/resources/live-test.sh) runs the live tests (but no other tests).
3. [`live-test-teardown.sh`](src/live-test/resources/live-test-teardown.sh) stops and removes the docker image.
