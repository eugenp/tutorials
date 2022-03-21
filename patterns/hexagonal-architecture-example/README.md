# Banking API - Hexagonal Architecture Example

## Description

This code repository implements a Java banking application to demonstrate the concepts of hexagonal architecture.

The app provides functionality for following use cases for a REST Api based interactions:

● Sending money between two predefined accounts with a positive starting balance

● Requesting account balance and list of transactions

### Requirements:

- Maven
- Java

### Starting(Application has to be run with dev profile):

- Open in IntelliJ, and run ExampleApplication

- Alternatively, run the application from command line with `mvn spring-boot:run` in the root of module

### Swagger API Documentation

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Examples of Api Usage


### Improvements:

- Allow for transactions/operations upto only 2 decimal places for account balance and transaction amounts
- Optimise service methods for minimising database connections

