# HEXAGONAL ARCHITECTURE - EXAMPLE IN JAVA

A Spring Boot base web application for address book has been built as part of this project based on the hexagonal architecture guidelines, which allows the user to add a contact ad list all the contacts that are available. 

### Running the application

The application can be run using maven spring-boot run command 

```
mvn spring-boot:run

```

New contact can be added through a POST request on http://loalhost:8080/contacts  with the body as 

```
{
	"name": "ABC",
	"address": "Address",
	"contactNumber": 1234567890
}

```
Existing contacts can be listed using a GET request on the same url http://loalhost:8080/contacts