The Courses
==============================


Here's the new "Learn Spring" course: <br/>
**[>> LEARN SPRING - THE MASTER CLASS](https://www.baeldung.com/learn-spring-course?utm_source=github&utm_medium=social&utm_content=tutorials&utm_campaign=ls#master-class)**

Here's the Master Class of "REST With Spring" (along with the new announced Boot 2 material): <br/>
**[>> THE REST WITH SPRING - MASTER CLASS](https://www.baeldung.com/rest-with-spring-course?utm_source=github&utm_medium=social&utm_content=tutorials&utm_campaign=rws#master-class)**

And here's the Master Class of "Learn Spring Security": <br/>
**[>> LEARN SPRING SECURITY - MASTER CLASS](https://www.baeldung.com/learn-spring-security-course?utm_source=github&utm_medium=social&utm_content=tutorials&utm_campaign=lss#master-class)**



Java and Spring Tutorials
================

This project is **a collection of small and focused tutorials** - each covering a single and well defined area of development in the Java ecosystem. 
A strong focus of these is, of course, the Spring Framework - Spring, Spring Boot and Spring Security. 
In additional to Spring, the modules here are covering a number of aspects in Java. 

Profile based segregation
====================

We are using maven build profiles to segregate the huge list of individual projects we have in our repository.

The projects are broadly divided into 3 list: first, second and heavy. 

Next, they are segregated further on the basis of tests that we want to execute.

Therefore, we have a total of 6 profiles:

| Profile                 | Includes                    | Type of test enabled |
| ----------------------- | --------------------------- | -------------------- |
| default-first           | First set of projects       | *UnitTest            |
| integration-lite-first  | First set of projects       | *IntegrationTest     |
| default-second          | Second set of projects      | *UnitTest            |
| integration-lite-second | Second set of projects      | *IntegrationTest     |
| default-heavy           | Heavy/long running projects | *UnitTest            |
| integration-heavy       | Heavy/long running projects | *IntegrationTest     |

Building the project
====================

Though it should not be needed often to build the entire repository at once because we are usually concerned with a specific module.

But if we want to, we can invoke the below command from the root of the repository if we want to build the entire repository with only Unit Tests enabled:

`mvn clean install -Pdefault-first,default-second,default-heavy`

or if we want to build the entire repository with Integration Tests enabled, we can do:

`mvn clean install -Pintegration-lite-first,integration-lite-second,integration-heavy`


Building a single module
====================
To build a specific module run the command: `mvn clean install` in the module directory


Running a Spring Boot module
====================
To run a Spring Boot module run the command: `mvn spring-boot:run` in the module directory


Working with the IDE
====================
This repo contains a large number of modules. 
When you're working with an individual module, there's no need to import all of them (or build all of them) - you can simply import that particular module in either Eclipse or IntelliJ. 


Running Tests
=============
The command `mvn clean install` from within a module will run the unit tests in that module.
For Spring modules this will also run the `SpringContextTest` if present.

To run the integration tests, use the command:

`mvn clean install -Pintegration-lite-first` or 

`mvn clean install -Pintegration-lite-second` or 

`mvn clean install -Pintegration-heavy`

depending on the list where our module exists





