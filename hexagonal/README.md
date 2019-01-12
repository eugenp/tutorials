## Introduction

The following [Spring Boot](https://spring.io/projects/spring-boot) application provides an
example of Hexagonal Architecture.  The app creates a simple greeting given a first and last name.


## Running the Example

To start the [Spring Boot](https://spring.io/projects/spring-boot) application, run the [Gradle](https://gradle.org/) script using
the following command.

```./gradlew bootRun```

After the dependencies download, and the application is compiled, the following should be logged to the console.

```o.s.web.servlet.DispatcherServlet        : Completed initialization in 9 ms```

## Primary Port Execution

Adapters are provided via JMX and HTTP.

### JMX Primary Adapter

To invoke a greeting via the JMX Adapter, first start JConsole by executing the following.

```jconsole```

In the Local Process list, find the Hexagonal Application, and connect to it.
Click on the MBeans tab, and navigate into the GreetingJmxResource.  There you should find
two operations to "sayHello" and "listAll."

After clicking on the "sayHello" operation, enter a first and last name in the 
invocation panel.  Then click the "sayHello" button.

You should see your greeting in a new dialog window.  

### HTTP Primary Adapter

To invoke a greeting via the HTTP Adapter, open a web browser to the following URL.

http://127.0.0.1:8080/greeting?nameFirst=Lukas&nameLast=Bradley

To retrieve the list of all greetings, use the following URL.

http://127.0.0.1:8080/greeting/list

## Secondary Port and Adapter

All greeting messages are stored within an in-memory [H2 Database]("http://www.h2database.com").
If you wish to peruse data, open your browser to the following.

http://127.0.0.1:8080/h2-console/

The JDBC Url is jdbc:h2:mem:hexagonal, username and password are hex.
