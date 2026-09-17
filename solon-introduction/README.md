## Solon Introduction

This module contains a REST API built with Solon 4.1.0, MyBatis-Flex 1.11.8,
HikariCP 7.1.0, and an in-memory H2 2.5.250 database. It targets Java 25.
Build toolchain: Eclipse Temurin JDK 25.0.4.1+1 and Maven 3.9.16.

### Running the application

From this directory:

```bash
mvn compile exec:java
```

The application listens on port 8080. The task data is discarded when the
application stops. To choose another port:

```bash
mvn compile exec:java -Dexec.args="--server.port=8081"
```

### Trying the endpoints

```bash
curl 'http://localhost:8080/hello?name=Baeldung'
curl -i -X POST http://localhost:8080/tasks \
  -H 'Content-Type: application/json' -d '{"title":"Learn Solon"}'
curl http://localhost:8080/tasks
curl http://localhost:8080/tasks/1
curl -X PUT http://localhost:8080/tasks/1 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Learn Solon REST APIs","completed":true}'
curl -i -X DELETE http://localhost:8080/tasks/1
```

Use the ID or `Location` header returned by POST for the subsequent requests.
POST creates an incomplete task and returns 201. GET and PUT return 200,
DELETE returns 204, missing tasks return 404, and invalid titles return 400.

### Running tests

```bash
mvn clean install
mvn clean install -Pintegration-jdk25
```

The first command runs the service unit tests. The integration-jdk25 profile reserves
an available HTTP port and runs the real HTTP tests against a separate H2
database. It also verifies that a service call inside a `@Rollback` test is
rolled back before cleanup. The multi-request CRUD test uses explicit cleanup
so that changes persist between its HTTP requests.

Solon's test extension requires a public test class. The `@Rollback` test method
is also public so that Solon's proxy intercepts it. Other JUnit 5 test methods
remain package-private.

The module participates in the repository's `default-jdk25` and `integration-jdk25`
profiles. From the repository root it can also be tested with:

```bash
mvn -pl solon-introduction -Pdefault-jdk25 clean install
mvn -pl solon-introduction -Pintegration-jdk25 clean install
```

Diagram sources and reproduction instructions are in `diagrams/info.txt`.
