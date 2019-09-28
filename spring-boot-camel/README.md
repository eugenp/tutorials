## Spring Boot Camel

This module contains articles about Spring Boot with Apache Camel

### Example for the Article on Camel API with SpringBoot

To start, run:

`mvn spring-boot:run`
	
Then, make a POST http request to:

`http://localhost:8080/camel/api/bean` 

Include the HEADER: Content-Type: application/json, 

and a BODY Payload like:

`{"id": 1,"name": "World"}`

We will get a return code of 201 and the response: `Hello, World` - if the transform() method from Application class is uncommented and the process() method is commented

or return code of 201 and the response: `{"id": 10,"name": "Hello, World"}` - if the transform() method from Application class is commented and the process() method is uncommented 

## Relevant articles:

- [Apache Camel with Spring Boot](http://www.baeldung.com/apache-camel-spring-boot)
