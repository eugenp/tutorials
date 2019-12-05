# keycloak-auth

## Description

Hexagonal Architectural simple example. It manages (Get, Create and Delete) SuperHeros and allows fighting them based on their power. It exposes an API with these operations:

- [GET] hero, get all super heros in the system
- [GET] here/{id}, get a specific hero
- [POST] hero, add a new hero
- [DELETE] hero/{id}, remove a hero
- [GET] hero/{id}/fight{id2}, fight two heros

## Technology

- Java 11
- Maven for Java dependency management
- Spring Boot 
- Lombok for the models

## How to deploy

Compile and package the project with

```
mvn clean package
```

and execute

```
java -jar target/auth.jar
```

It can also be run as:

```
mvn spring-boot:run
```

Go to your browser and type http://localhost:8081/swagger-ui.html

Use the application properties according to your Keycloak server configuration.

## License

Apache 2.0