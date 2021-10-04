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

    
The Wire Tap processor, by default, makes a shallow copy of the Camel Exchange instance. The copy of the exchange is sent to the endpoint specified in the wireTap statement. The body of the wire tapped message contains the same object as that in the original message which means any change to the internal state of that object during the wire tap route may also end up changing the main message’s body.

To solve this, we need to create a deep copy of the object before passing it to the wire tap destination. Wire Tap EIP provides us with a mechanism to perform a “deep” copy of the message, by implementing the org.apache.camel.Processor class. This needs to be be called using onPrepare statement right after wireTap.
For more details, check out the AmqApplicationUnitTest.class.

### Relevant Articles:

- [Wire Tap Enterprise Integration Pattern](https://www.baeldung.com/wiretap-pattern)

