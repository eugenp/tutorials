## JMeter

This module contains articles about JMeter.
It contains the code of a simple API for some CRUD operations built using Spring Boot.

### Requirements

- Maven
- JDK 8

### Running

To build and start the server simply type

```bash
$ mvn clean install
$ mvn spring-boot:run -Dserver.port=8989
```

### Available CRUD

You can see what crud operation are available using curl:

```bash
$ curl localhost:8080
```

### Available UUID API

You can view the test response using curl:

```bash
$ curl localhost:8080/api/uuid
```

Now with default configurations it will be available at: [http://localhost:8080](http://localhost:8080)

Enjoy it :)

### Relevant Articles:

- 