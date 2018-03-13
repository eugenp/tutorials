# mvc-exceptions

NOTES:

  * This project is built using release 1.1.8 of Spring Boot, Java 8 and Spring 4.1 - see pom.xml.
  * The POM builds a JAR file, not a WAR, so you must run it as a Java application.  Use `mvn exec:java` or `mvc spring-boot:run` to run it, then goto ```http://localhost:8080```.
 
This application demos most of the points covered on my MVC Exceptions blog:
<a href="https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc">
https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc</a>.

<b>This demo is running on <a href="http://run.pivotal.io/">Cloud Foundry</a> at <a href="http://mvc-exceptions-v2.cfapps.io/">http://mvc-exceptions-v2.cfapps.io/</a>.</b>

#### Release History
  * November 2013: V1
  * October 2014: V2

## Application Overview 

The most significant files are:

### Demo 1

Controller with @ExceptionHandler methods

  * <code>src/main/java/demo1/web/ExceptionHandlingController.java</code>
     * A controller that raises exceptions and provides its own handlers to catch and process them.

### Demo 2

Controller relying on a ControllerAdvice to handle its exceptions.

  * <code>src/main/java/demo2/web/ControllerWithoutExceptionHandlers.java</code>
     * A controller with all the same handlers as `ExceptionHandlingController` (so they all throw exceptions)
       but no handler methods.
  * <code>src/main/java/demo2/web/GlobalControllerExceptionHandler.java</code>
     * `@ControllerAdvice` class with all the same handlers as `ExceptionHandlingController`, but they would
       apply to all controllers.

### Demo 3 and 4

Exception handling using a `SimpleMappingExceptionResolver`.  When running in demo
mode (profile is set to `demo-config`, which is setup by default), it defines a
`SimpleMappingExceptionResolver` subclass that can be enabled (Demo 3) or disabled
(Demo 4) to show the difference.

   * <code>src/main/java/demo3/config/DemoExceptionConfiguration.java</code>
       * Java configuration to setup the beans for this demo.
   * <code>src/main/java/demo3/web/ExceptionThrowingController.java</code>
       * Controller used by the demo.
   * <code>src/main/java/demo3/web/SwitchableSimpleMappingExceptionResolver.java</code>
       * The resolver subclass described above.
   * <code>src/main/java/demo3/web/ExceptionThrowingController.java</code>
       * Controller that provides `/simpleMappingExceptionResolver/on` and
         `/simpleMappingExceptionResolver/off` for switching the resolver on/off.

### Demo 5

   * <code>ReturnOrRedirectController</code>
       * Controller highlighting how Spring Boot implements its error-page mechanism.
       
### Exceptions

  * <code>src/main/java/demo/exceptions/CustomException.java</code>
  * <code>src/main/java/demo/exceptions/DatabaseException.java</code>
  * <code>src/main/java/demo/exceptions/InvalidCreditCardException.java</code>
  * <code>src/main/java/demo/exceptions/OrderNotFoundException.java</code>
  * <code>src/main/java/demo/exceptions/UnhandledException.java</code>
      * Custom exceptions - see blog for usage.
  * <code>src/main/java/org/springframework/dao/DataAccessException.java</code>
     * Example of a predefined annotation, copied from Spring.
  * <code>src/main/java/org/springframework/dao/DataIntegrityViolationException.java</code>
     * Example of a predefined annotation, copied from Spring.
  
### Application Setup

The Demo configuration profile is useful for this demo application, but not typical.  So two other profiles are provided
to configure a `SimpleMappingExceptionResolver` in a more typical way using either Java configuration or an XML
bean file.

  * <code>src/main/java/demo/main/Main.java</code>
     * Main entry point for the application.  Can run as a Java application (using an embedded Tomcat container)
       or as a WAR inside a container.  Sets a few initialization properties and Spring Bean profile to use.
       Available profiles are `demo-config` (default), `java-config`, `xml-config`.
  * <code>src/main/java/demo/main/Profiles.java</code>
    * The Spring Bean profiles used in the application.
  * <code>src/main/java/demo/config/ExceptionConfiguration.java</code>
     * Java configuration class to setup a `SimpleMappingExceptionResolver`. Only used if
       the `java-config` profile is active.
  * <code>src/main/resources/mvc-configuration.xml</code>
     * XML alternative to `ExceptionConfiguration`. Also sets up a
       `SimpleMappingExceptionResolver`. Only used if the `xml-config` profile is active.
  * <code>src/main/java/demo/config/ResponseDataControllerAdvice</code>
      * Controller advice that puts useful data into the model for every request.

### Utility Classes

  * <code>src/main/java/demo/utils/BeanLogger.java</code>
     * Simple BeanPostProcessor to log all beans created.  Not required by the demo, but as Spring Boot is new,
       it allows all the beans created to be logged.

### Templates

All the views used, generated via Thymeleaf.

  * <code>src/main/resources/templates/creditCardError.html</code>
  * <code>src/main/resources/templates/databaseError.html</code>
  * <code>src/main/resources/templates/databaseException.html</code>
  * <code>src/main/resources/templates/error.html</code>
  * <code>src/main/resources/templates/exceptionPage.html</code>
  * <code>src/main/resources/templates/support.html</code>
  * <code>src/main/resources/templates/index.html</code> (home page)

### Build

  * <code>pom.xml</code>
     * Maven POM - notice short it is - Spring Boot does most of the work.  However heed the comments in the file.

### Examples

Not used by the application, but provided as sample code.
     
  * <code>src/main/java/demo/example/ExampleExceptionHandlerExceptionResolver.java</code>
     * Unused in the demo (to keep it simple), but implements the example discussed in the blog.
  * <code>src/main/java/demo/example/ExampleSimpleMappingExceptionResolver.java</code>
     * Unused in the demo (to keep it simple), but implements the example shown in the blog
       (called `MySimpleMappingExceptionResolver` in the blog article).


