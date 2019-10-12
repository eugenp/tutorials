## Spring Boot Admin

This module contains articles about Spring Boot Admin

## 1. Spring Boot Admin Server

* mvn clean install
* mvn spring-boot:run
* starts on port 8080
* login with admin/admin
* to activate mail notifications uncomment the starter mail dependency
and the mail configuration from application.properties
* add some real credentials if you want the app to send emails
* to activate Hipchat notifications proceed same as for email

## 2. Spring Boot App Client

* mvn clean install
* mvn spring-boot:run
* starts on port 8081
* basic auth client/client


### Relevant Articles: 

- [A Guide to Spring Boot Admin](https://www.baeldung.com/spring-boot-admin)
