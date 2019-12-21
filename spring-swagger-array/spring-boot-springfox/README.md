# Array of Strings on body parameter in Swagger API

## Description

Swagger simple example. It manages (Get and Create) SuperHeros and allows fighting them based on their power. It exposes an API with these operations:

- [GET] array, get all the arrays in the system
- [POST] array, add a new array

## Technology

- Java 11
- Maven for Java dependency management
- Spring Boot 
- Lombok for the model
- Swagger and Springfox for documenting the REST APIs

## How to deploy

Run the project with:

```
mvn spring-boot:run
```

Go to your browser and type http://localhost:8080/swagger-ui.html

## License

Apache 2.0