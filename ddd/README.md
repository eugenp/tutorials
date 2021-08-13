## Domain-driven Design (DDD)

This module contains articles about Domain-driven Design (DDD)

### Relevant articles

- [Persisting DDD Aggregates](https://www.baeldung.com/spring-persisting-ddd-aggregates)
- [Double Dispatch in DDD](https://www.baeldung.com/ddd-double-dispatch)
- [Organizing Layers Using Hexagonal Architecture, DDD, and Spring](https://www.baeldung.com/hexagonal-architecture-ddd-spring)


### Building and running the HexagonalDraft application

To build the application run this command in the project directory:
mvn clean install

To start the application run this command:
mvn spring-boot:run

The endpoints can be accessed on:
1. GET list of products:
http://localhost:8080/api/v1/product

2. GET product by productID:
http://localhost:8080/api/v1/product/$productID

3. POST create new product
http://localhost:8080/api/v1/product

3. Delete product by productID
http://localhost:8080/api/v1/product/$productID

### Postman collection to import sample endpoint
- [Link to Postman sample](https://github.com/sachin071287/tutorials/ddd/HexagonalDraft.postman_collection.json)
