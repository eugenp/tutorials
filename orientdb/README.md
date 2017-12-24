Introduction to the OrientDB Java APIs
======================================

This is a simple maven project that shows how to do OrientDB operations using the Java API.

### Requirements

- Maven
- Java 7 or higher
- OrientDB

### Running

To build and start the server simply type

```bash
$ mvn clean install
```

### Test Db Operation

You can see what crud operation are available using curl:

```bash
$ curl localhost:8080
```
You can view existing student objects with this command:

```bash
$ curl localhost:8080/articles
```


Now with default configurations it will be available at: [http://localhost:8080](http://localhost:8080)

Enjoy it :)