## Spring REST Full

This module contains articles about REST APIs with Spring

### Courses

The "REST With Spring" Classes: http://bit.ly/restwithspring

The "Learn Spring Security" Classes: http://github.learnspringsecurity.com

### Relevant Articles: 
- [Integration Testing with the Maven Cargo plugin](http://www.baeldung.com/integration-testing-with-the-maven-cargo-plugin)
- [Project Configuration with Spring](http://www.baeldung.com/project-configuration-with-spring)
- [Metrics for your Spring REST API](http://www.baeldung.com/spring-rest-api-metrics)
- [Spring Security Expressions - hasRole Example](https://www.baeldung.com/spring-security-expressions-basic)


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
curl http://localhost:8082/spring-rest-full/auth/foos
```
