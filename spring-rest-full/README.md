=========

## REST Example Project with Spring

### Courses
The "REST With Spring" Classes: http://bit.ly/restwithspring

The "Learn Spring Security" Classes: http://github.learnspringsecurity.com

### Relevant Articles: 
- [REST Pagination in Spring](http://www.baeldung.com/rest-api-pagination-in-spring)
- [HATEOAS for a Spring REST Service](http://www.baeldung.com/rest-api-discoverability-with-spring)
- [REST API Discoverability and HATEOAS](http://www.baeldung.com/restful-web-service-discoverability)
- [ETags for REST with Spring](http://www.baeldung.com/etags-for-rest-with-spring)
- [Integration Testing with the Maven Cargo plugin](http://www.baeldung.com/integration-testing-with-the-maven-cargo-plugin)
- [Introduction to Spring Data JPA](http://www.baeldung.com/the-persistence-layer-with-spring-data-jpa)
- [Project Configuration with Spring](http://www.baeldung.com/project-configuration-with-spring)
- [Metrics for your Spring REST API](http://www.baeldung.com/spring-rest-api-metrics)
- [Spring RestTemplate Tutorial](http://www.baeldung.com/rest-template)
- [Bootstrap a Web Application with Spring 4](http://www.baeldung.com/bootstraping-a-web-application-with-spring-and-java-based-configuration)
- [Build a REST API with Spring 4 and Java Config](http://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-configuration)
- [Error Handling for REST with Spring](http://www.baeldung.com/exception-handling-for-rest-with-spring)



### Build the Project
```
mvn clean install
```


### Set up MySQL
```
mysql -u root -p 
> CREATE USER 'tutorialuser'@'localhost' IDENTIFIED BY 'tutorialmy5ql';
> GRANT ALL PRIVILEGES ON *.* TO 'tutorialuser'@'localhost';
> FLUSH PRIVILEGES;
```


### Use the REST Service

```
curl http://localhost:8080/spring-rest-full/foos
```
