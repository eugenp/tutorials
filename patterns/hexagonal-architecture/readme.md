## A simple spring boot application illustrating hexagonal architecture in java

### Usage


- controllers Urls
    * `/student/register`: create a new student 
    * `/student/[registration_Id]`: retrieve the student by the registration id.

### Build and run

#### Configurations

Open the `application.properties` file and set your own configurations for the
database connection.

#### Prerequisites

- Java 7
- Maven 3

#### From terminal

Go on the project's root folder, then type:

    $ mvn spring-boot:run

#### From Eclipse (Spring Tool Suite)

Import as *Existing Maven Project* and run it as *Spring Boot App*.
