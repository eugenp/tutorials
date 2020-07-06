## Hexagonal architecture demo with Spring Boot

To build the application, run:

```
mvn clean package
```

To run the application, run:

```
mvn spring-boot:run
```

The application starts listening for HTTP requests on `localhost:8080`.

### Endpoints

This is a simple library simulation. To add new books to the library, modify the `data.sql` script in `src/main/resources`.
It will update the state of the app during startup.

List all available books
```
GET /books 
```

Borrow a book
```
POST /books/{id}/borrow/{name-of-borrower}
```

Return a book
```
POST /books/{id}/return
```



