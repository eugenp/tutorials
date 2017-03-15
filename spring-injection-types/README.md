#Different Types of Bean Injections in Spring#

This repository contains examples for different types of bean injections. It contains a simple spring boot application which defines bean injections in two ways.

##Constructor Based Injection##

##Setter Based Injection##

The application uses maven, following commands can be used to build and run the application

> mvn install

> mvn spring-boot:run


Once application is started, it can be accessed using


method: POST
Request Body:
{
  "firstName": "...",
  "lastName": "...",
  "department": "SALES",
  "address": {
    "country": "USA",
    "state": "CA",
    "city": "ABC",
    "street": "Street 1",
    "zipcode": "94522"
  }
}
> http://localhost:8080/employee 


method: GET
> http://localhost:8080/employee

Response Body:

{
  "firstName": "...",
  "lastName": "...",
  "department": "...",
  "address": {
    "country": "...",
    "state": "...",
    "city": "...",
    "street": "...",
    "zipcode": "..."
  }
}