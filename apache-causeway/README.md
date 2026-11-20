# Apache Causeway Asset Management

This module contains a small internal asset management application built with Apache Causeway.

It requires Java 21 or later and Maven 3.9.11 or later. To build and start the application:

```shell
mvn clean install
mvn spring-boot:run
```

The Wicket UI is available at `http://localhost:8080/wicket/`. Sign in with username `admin` and password `pass`.
The generated Restful Objects API is rooted at `http://localhost:8080/restful/` and uses the same credentials.

The in-memory database and credentials are intended for local demonstration only.
