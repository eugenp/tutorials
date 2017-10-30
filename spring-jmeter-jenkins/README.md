BASIC CRUD API with Spring Boot
================================

This is the code of a simple API for some CRUD operations realised for a seminar at [FGI](www.fgi-ud.org) using Spring Boot.

### Demo
* API:  The online version **is**/**will be** hosted here: https://fgi-tcheck.herokuapp.com
* Mobile version is also opensource and located here: https://github.com/valdesekamdem/tcheck-mobile

### Features
#### Currently Implemented
* CRUD
  * Student
  
#### To DO
* Validations of input with: [Spring Data Rest Validators](http://docs.spring.io/spring-data/rest/docs/2.1.0.RELEASE/reference/html/validation-chapter.html)


### Requirements

- Maven
- JDK 8
- MongoDB

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


Now with default configurations it will be available at: [http://localhost:8080](http://localhost:8080)

Enjoy it :)