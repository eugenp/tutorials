# A short example of real-time event streaming using Spring Webflux

### Description

This is a quick and practical example of the Spring Webflux support on the server side, and on the client side, a simple Reactive Webclient.

This __INITIAL__ version contains the article example, the backend produces one event per second infinitely and the client side consumes and log the events as they arrive.


###1. Overview
__Spring Webflux__ is a new module of __Spring 5__ that allows us to work with the __Reactive programming__ concept. Basically, it’s a paradigm based on the Observer design pattern where we have the Publisher object, who sends data; and the Subscriber object, who processes it.

Below, we explain how to develop a simple server-side publisher who will send recursive events. We also focus on how to process these events on reactive web clients (subscribers).

###2. Configure
Let’s start by seeing how to configure our project to use __Spring Webflux__. First of all, we need to add the following dependency to the pom.xml:


> ##### <dependency>
> #####     <groupId>org.springframework</groupId>
> #####     <artifactId>spring-webflux</artifactId>
> ##### </dependency>

### 3. Server-side – Publisher 
The server-side client is just a simple rest endpoint who will return a Flux object. This class has the publisher’s streams and will inform the subscribed web clients about the changes. Now, let´s take a look at this endpoint:

> ##### @RestController
> ##### public class PublisherController {  
> #####     @GetMapping(path = "createEvent", produces = "text/event-stream")
> #####     public Flux<Long> createEvent() {
> #####         return Flux.interval(Duration.ofMillis(1000)).
> #####                 map(index->index);
> #####     }
> ##### }

This method will send an infinite number’s list, one per second. Accordingly, the web client reacts to this action each time a number is sent.

### 4. Reactive web-client – Subscriber
The reactive web clients process the Server-Sent events, captured via HTTP, and proceed changing their statuses.

Note that each programming language has his specific frameworks to manage the SSEs. However, for our example, we will keep it simple using the following web client to process the received numbers:

> ##### <html>
> #####     <body>
> #####         <div id="content"></div>
> #####         <script>
> #####             var source = new EventSource("createEvent");
> #####             source.addEventListener('message', function (e) {
> #####                 const index = event.data;
> #####                 const content = "New number received: " + index + "<br>";
> #####                 document.getElementById("content").innerHTML += content;
> #####             }, false);
> #####         </script>
> #####     </body>
> ##### <html>

This simplistic web client is listening to the endpoint createEvent, previously shown, and will print the newly received number.

###5. Conclusion
In conclusion, we now have a simple way to take advantage of this programming paradigm with Spring Webflux and the use of Server-Sent events. Hence, this supposes an alternative to WebSockets and the use of full-duplex communications.

The implementation of this example can be checked in the GitHub project. It is a Maven-based project, so execute the Spring Boot application and call index.html locally to see how it’s working.

### Relevant Articles

- [Concurrent Test Execution in Spring 5](http://www.baeldung.com/spring-5-concurrent-tests)
- [Introduction to the Functional Web Framework in Spring 5](http://www.baeldung.com/spring-5-functional-web)
- [Exploring the Spring 5 MVC URL Matching Improvements](http://www.baeldung.com/spring-5-mvc-url-matching)
- [Spring 5 WebClient](http://www.baeldung.com/spring-5-webclient)
- [Spring 5 Functional Bean Registration](http://www.baeldung.com/spring-5-functional-beans)
- [The SpringJUnitConfig and SpringJUnitWebConfig Annotations in Spring 5](http://www.baeldung.com/spring-5-junit-config)
- [Spring Security 5 for Reactive Applications](http://www.baeldung.com/spring-security-5-reactive)
- [Spring 5 Testing with @EnabledIf Annotation](https://github.com/eugenp/tutorials/tree/master/spring-5)
- [Reactive WebSockets with Spring 5](http://www.baeldung.com/spring-5-reactive-websockets)