# Cloning the repository

If you are getting an error while cloning the repository, try running:
git config --global http.postBuffer 5000000

This will increase the buffer size from the default 1MiB to 5MiB.

To revert this value to the default, use:
git config --global http.postBuffer 1000000
 

The Courses
==============================


"Learn Spring" Course: <br/>
**[>> LEARN SPRING - THE MASTER CLASS](https://www.baeldung.com/learn-spring-course?utm_source=github&utm_medium=social&utm_content=tutorials&utm_campaign=ls#master-class)**

"REST With Spring" Course: <br/>
**[>> THE REST WITH SPRING - MASTER CLASS](https://www.baeldung.com/rest-with-spring-course?utm_source=github&utm_medium=social&utm_content=tutorials&utm_campaign=rws#master-class)**

"Learn Spring Security" Course: <br/>
**[>> LEARN SPRING SECURITY - MASTER CLASS](https://www.baeldung.com/learn-spring-security-course?utm_source=github&utm_medium=social&utm_content=tutorials&utm_campaign=lss#master-class)**



Java and Spring Tutorials
================

This project is **a collection of small and focused tutorials** - each covering a single and well-defined area of development in the Java ecosystem. 
A strong focus of these is the Spring Framework - Spring, Spring Boot and Spring Security. 
In addition to Spring, the modules here cover several aspects of Java. 

Profile-based segregation
====================

We use Maven build profiles to segregate the huge list of individual projects in our repository.

The projects are broadly divided into 4 lists: default, default-jdk17, default-jdk8 and default-heavy. 

Next, they are segregated further based on the tests that we want to execute.

We also have a parents profile to build only parent modules.

Therefore, we have a total of 9 profiles:

| Profile            | Includes                    | Type of test enabled         |
|--------------------|-----------------------------|------------------------------|
| default            | JDK21 projects              | *UnitTest                    |
| integration        | JDK21 projects              | *IntegrationTest             |
| default-jdk17      | JDK17 projects              | *UnitTest                    |
| integration-jdk17  | JDK17 projects              | *IntegrationTest             |
| profile-jdk22      | JDK22 projects              | *UnitTest & *IntegrationTest |
| profile-jdk23      | JDK23 projects              | *UnitTest & *IntegrationTest |
| default-heavy      | Heavy/long running projects | *UnitTest                    |
| integration-heavy  | Heavy/long running projects | *IntegrationTest             |
| default-jdk8       | JDK8  projects              | *UnitTest                    |
| integration-jdk8   | JDK8  projects              | *IntegrationTest             |
| parents            | Set of parent modules       | None                         |

Building the project
====================

Though it should not be needed often to build the entire repository at once because we are usually concerned with a specific module.

But if we want to, we can invoke the below command from the root of the repository if we want to build the entire repository with only Unit Tests enabled:

`mvn clean install -Pdefault,default-heavy`

or if we want to build the entire repository with Integration Tests enabled, we can do:

`mvn clean install -Pintegration,integration-heavy`

Analogously, for the JDK8 projects the commands are:

`mvn clean install -Pdefault-jdk8`

and

`mvn clean install -Pintegration-jdk8`

Building a single module
====================
To build a specific module, run the command: `mvn clean install` in the module directory.

It can happen that your module is part of a parent module e.g. `parent-boot-1`,`parent-spring-5` etc, then you will need to build the parent module first so that you can build your module.
We have created a `parents` profile that you can use to build just the parent modules, just run the profile as:
`mvn clean install -Pparents`


Building modules from the root of the repository
====================
To build specific modules from the root of the repository, run the command: `mvn clean install --pl akka-modules,algorithms-modules -Pdefault` in the root directory.

Here `akka-modules` and `algorithms-modules` are the modules that we want to build and `default` is the maven profile in which these modules are present.


Running a Spring Boot module
====================
To run a Spring Boot module, run the command: `mvn spring-boot:run` in the module directory.


Working with the IDE
====================
This repo contains a large number of modules. 
When you're working with an individual module, there's no need to import all of them (or build all of them) - you can simply import that particular module in either Eclipse or IntelliJ. 


Running Tests
=============
The command `mvn clean install` from within a module will run the unit tests in that module.
For Spring modules this will also run the `SpringContextTest` if present.

To run the integration tests, use the command:

`mvn clean install -Pintegration` or

`mvn clean install -Pintegration-heavy` or

`mvn clean install -Pintegration-jdk8`

depending on the list where our module exists
