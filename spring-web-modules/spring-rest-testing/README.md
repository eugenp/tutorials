## Spring REST Testing

This module contains articles about testing REST APIs with Spring

### Courses

The "REST With Spring" Classes: http://bit.ly/restwithspring

The "Learn Spring Security" Classes: http://github.learnspringsecurity.com

### Relevant Articles: 

- [Integration Testing with the Maven Cargo plugin](https://www.baeldung.com/integration-testing-with-the-maven-cargo-plugin)
- [Testing Exceptions with Spring MockMvc](https://www.baeldung.com/spring-mvc-test-exceptions)

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
