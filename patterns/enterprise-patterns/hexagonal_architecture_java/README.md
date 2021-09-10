Hexagonal Architecture:

Overview

This article will evaluate and illustrate the implementation of a Hexagonal architecture also known as the Ports and Adapter pattern in springboot.

In this article we will refer to our business logic,rules and models as the core application.

The Hexagonal architecture was invented by Alistar Cockburn as a solution to frequently occurring structural problems in object-oriented applications, this pattern was invented for the following benefits: Easy and quick maintainability,Technical depth reduction, separation of concerns and business logic preservation.
The Hexagonal tag does not necessarily mean it is a 6 sided architecture, it’s more indicative of a multi-port/adapter situation.

It’s also important to note that the resultant project structure can be implemented as you see fit; however we have provided the general guidelines which we will implement in this tutorial.

At the heart of a hexagonal architecture is the Domain or core application, so we will start our implementations here.
In the middle we have the Application layer, this and everything else is a slave to the core application, so your input and output ports (interfaces)  should be defined based on the requirements of the core application and not the other way around, This makes sense because any changes to the core application, model or logic is usually indicative of a changing business requirement and as such you don’t want this changing every time you make an infrastructural change.
This is the Framework layer, the application connects to every data source using adapters, whether we’re making an input via http to the server or storing outputs to a database we need to use detachable adapters.

We’ll be building a wallet based application for buying food items, and we’ll call it kitchen-assistant.

Business Models
First we’ll create business models in the “domain” package, next we’ll create the  User,Wallet,Address and Orders classes, these will usually contain business rules and validation logic represented by methods.

Ports
Next we’ll create an “application” package with two sub packages one for “ports(interfaces)” and “service” classes.
The “in” package contains dtos, and the OrderItems interface with two methods for creating and retrieving orders then we implement this interface by creating a service class OrderItemsService in the service package, we can probably begin to see how this qualifies as a port since our controller class will only ever be aware of this component.

The “out” package contains ports for external communications, in this case reading from the database, and serving the controller, this is why we have the two ports LoadUserPort and OrderEntryPort.

Adapters
Finally the adapter layer, we create the adapter package following the same pattern as earlier we name the sub packages as follows:
“In.web” : contains the controller class which uses an instance of OrderItems, an input port to communicate with the core application.

“Out.persistence” : similarly contains the persistence abstractions in this case JPA entities,repositories and an adapter that ensures consistent communication when reading and writing from the DB, (basically object mapping).

Note: For the sake of this tutorial we implemented an Application event listener to create a user every time the application starts so we have a user to simulate a test with.

Conclusion

As you can probably tell by now every component is connected loosely which means we can replace them as we see fit, for example we could easily change our persistence implementation to a non-relational database for the OrderEntries and all we would need to do is re-implement OrderEntryPort, and we would be done, or we could switch to GRPC and remove the controller class without worrying about breaking the core application.

This is a reliable and highly structural design pattern, It offers massive benefits like ease of modifications, loose coupling and the flexibility to quickly add or replace systems.
These are very useful when you’re building extremely complex or regulated pieces of software some of which may include banking / finance applications or multi level maker-checker applications, but if the use case is for something simpler with less business rules then we would be introducing the unnecessary cons of this architecture which include duplicated classes and interfaces and  its extra complexity.
