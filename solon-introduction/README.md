## Solon Introduction

This module contains a Solon REST API backed by MyBatis-Flex, HikariCP, and an
in-memory H2 database. It requires JDK 21 and Maven 3.9 or later.

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
mvn clean install -Pintegration
```

The first command runs the service unit tests. The integration profile reserves
an available HTTP port and runs the real HTTP tests against a separate H2
database. It also verifies that a service call inside a `@Rollback` test is
rolled back before cleanup. The multi-request CRUD test uses explicit cleanup
so that changes persist between its HTTP requests.

Solon's test extension requires a public test class. The `@Rollback` test method
is also public so that Solon's proxy intercepts it. Other JUnit 5 test methods
remain package-private.

The module participates in the repository's `default` and `integration`
profiles. From the repository root it can also be tested with:

```bash
mvn -pl solon-introduction -Pdefault clean install
mvn -pl solon-introduction -Pintegration clean install
```

Diagram sources and reproduction instructions are in `diagrams/info.txt`.
