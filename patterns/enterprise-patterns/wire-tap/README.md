# Wire Tap Pattern

The application shows you how to use a Wire Tap to monitor, debug or troubleshoot messages flowing through the system, without permanently consuming them off, or making any changes to the expected message in the output channel.

This example shows how to implement this with a simple Apache Camel application using Spring Boot and Apache ActiveMq.
For convenience, we are using in-memory activeMq.

 

### Configuring and using the Connection Factory

1. Create CamelContext.
2. Connect to embedded (or remote) ActiveMQ JMS broker.
3. Add JMS queue to CamelContext. 
4. Load file orders (xml/csv) from src/data into the JMS queue.
5. Based on the extension of the incoming file message, route to the respective queues.
6. Test that the destination route is working.
7. Audit the received file (order) from the wire tap queue.

### How to run the example:

    mvn spring-boot:run
