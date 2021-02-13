## JMeter

This module contains articles about JMeter.
It contains the code of a simple API for some CRUD operations built using Spring Boot.

### Requirements

- Maven
- JDK 8
- MongoDB (Note: for the Write Extracted Data to a File Using JMeter example MongoDB is not required)

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
You can view existing student objects with this command:

```bash
$ curl localhost:8080/students
```
Or create a new one via a POST:

```bash
$ curl -X POST -H "Content-Type:application/json" -d '{ "firstName" : "Dassi", "lastName" : "Orleando", "phoneNumber": "+237 545454545", "email": "mymail@yahoo.fr" }' localhost:8080/students
```

### Available Test API

You can view the test response using curl:

```bash
$ curl localhost:8080/api/test
```

Now with default configurations it will be available at: [http://localhost:8080](http://localhost:8080)

Enjoy it :)

### Relevant Articles:

- [Intro to Performance Testing using JMeter](https://www.baeldung.com/jmeter)
- [Configure Jenkins to Run and Show JMeter Tests](https://www.baeldung.com/jenkins-and-jmeter)
- [Write Extracted Data to a File Using JMeter](https://www.baeldung.com/jmeter-file-extraction)
