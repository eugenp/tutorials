# lab-hexagon-java

This is a starter project based on:

* Java 
* Spring Framework
* Maven multi-module structure

Inspired by the patterns:

* Hexagonal Architecture
* CQRS
* Domain event

The Hexagonal pattern is described by Alistair Cockburn in his blog:

*“Allow an application to equally be driven by users, programs, automated test or batch scripts, and to be developed and tested in isolation from its eventual run-time devices and databases"* 

You can find o better information about the design of this solution in the presentation [Hexagonal Architecture ](http://fabricioepa.wordpress.com/2015/02/04/hexagonal-architecture)

I should remeber you these patterns are not exclusively related to the Java technologies and frameworks.

# The project structure

### 1. ticketapp (/pom.xml)
 Main parent maven project aggregates all project modules

### 2. ticketapp-core-api (/core/api/pom.xml)
 This module plays the role **PORT** for the hexagon architecture. 
 
 Service port definition -> All service interfaces and API objects to acccess the **CORE** application. 

### 3. ticketapp-core-application (/core/application/pom.xml)
 This modules plays the **CORE** role in the hexagonal pattern:
 Implementation of the application services, event handlers, domain model entities and repositories.

### 4. ticketapp-adapter-rest  (/integration/rest-adapter/pom.xml)
 The RESTful API **ADAPTER** for the application service **PORT** (core-api)
 
The adapters projects (/integration/\*) are dependent on the core-api, they do not know at compilation time the core-application, the concrete core-application will be provided just at runtime by the **Dependency Injection Container**.

# My professional experience using this technique
 
 It was very simple to create a decoupled REST adapter from the core application, it should
 be simple to create another kind of adapter too.
 However, if the application domain is quite BIG or there are many ports and adapters, you will have some overhead to maintain different domains translated from the original application core domain.
The **PORT** interface should also have a stable API definition, you should design it to support evolution without easily break compatibility with the adapters implementations for that **PORT**.  
As inital strategy to achieve that design, the project has domain events to encapsulate all input and output data for the service, enabling the application to evolute without directly break the adapter code of the API.
 
 `core/api/src/main/java/com/ticketapp/core/api/TicketService.java`
```java
/**
 * Ticket Service - Use Case API
 */
public interface TicketService {

	TicketsReadEvent list(ReadTicketsEvent event);

	TicketCreatedEvent create(CreateTicketEvent ticket);

	TicketUpdatedEvent update(UpdateTicketEvent ticket);

	TicketDeletedEvent delete(DeleteTicketEvent ticket);
}
```

 This is one of the reasons the pattern has often been applied to microservices architecture, because 
 it works nice for multiple system integrations and also for small/medium sized application domains.
 
 If the application domain starts to grow up, may be it is the time to think about design a new hexagon to it.
 
 Feel free to bring up your ideas  ;)
