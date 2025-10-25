1. Introduction
In this article, we’ll learn what Dapr is, how it integrates with Spring Boot, and how to create a publish/subscribe system without coupling to specific brokers. We’ll walk through a ride-hailing scenario where users request rides and drivers subscribe to those requests. Ultimately, we’ll implement tests that run without the Dapr CLI or external infrastructure.

2. Implementing an Agnostic Infrastructure With Dapr
Distributed systems often come with common yet complex challenges. We usually address these problems using a mix of vendor-specific libraries, infrastructure tools, and manual integration work.

Dapr (Distributed Application Runtime) provides a set of APIs and building blocks to address these challenges, abstracting away infrastructure so we can focus on business logic. These principles apply to other concerns such as calling other services (via the service invocation API), persisting state (via the state management API), or retrieving secrets (via the secrets API).

This decoupling makes applications easier to test, more portable across environments, and more resilient to infrastructure changes. In this article, we’ll focus on the pub/sub API to illustrate these benefits in practice.

2.1. Bridging Spring Messaging With Dapr
Spring Boot has a strongly opinionated integration model, particularly around messaging. Many developers are already familiar with Spring abstractions, such as KafkaTemplate, RabbitTemplate, and their listener counterparts. While these simplify broker integration, they remain tightly coupled to specific technologies.

Instead of being just another API, the dapr-spring-boot-starter project provides seamless integration. It uses familiarly named interfaces, such as DaprMessagingTemplate and @Topic. These make it easy to utilize Dapr’s distributed messaging capabilities without needing to know the underlying infrastructure details.

More specifically, by including the Dapr Spring Boot starter, we don’t need to include any specific broker dependency. This enables swapping brokers without any code changes. Configuring provider-specific features at the component level is also possible without changing application code. For example, we can include a Kafka-specific setup to leverage native features like consumer groups.

2.2. Having Infrastructure Flexibility Without Lock-in
Dapr decouples application code from infrastructure. For example, whether we’re using Kafka, RabbitMQ, Redis Streams, or even Azure Service Bus under the hood, our Spring Boot application communicates with the Dapr sidecar via HTTP or gRPC, and Dapr handles the integration with the actual broker.

Most importantly, we can test locally without a complete infrastructure, as we’ll see using Testcontainers. The dapr-spring-boot-starter-test module spins up Dapr sidecars as part of the test lifecycle, eliminating the need to learn the Dapr CLI or Kubernetes.

3. Setting Up the Spring Boot Project
We’ll mock a ride-hailing app to demonstrate how Dapr integrates with Spring Boot. Users will send ride requests to our API endpoint, which publishes a message to subscribed drivers. Drivers can then choose whether to accept the ride.

Let’s start by adding the required dependencies. We’ll need spring-boot-starter-web for our REST endpoints and dapr-spring-boot-starter for Spring Boot integration:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>io.dapr.spring</groupId>
    <artifactId>dapr-spring-boot-starter</artifactId>
    <version>1.16.0</version>
</dependency>
```
For testing, we’ll also add the dapr-spring-boot-starter-test for Testcontainers support and the RabbitMQ container as our message broker:
```xml
<dependency>
    <groupId>io.dapr.spring</groupId>
    <artifactId>dapr-spring-boot-starter-test</artifactId>
    <version>1.16.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId> 
    <artifactId>rabbitmq</artifactId>
    <version>1.21.3</version>
    <scope>test</scope>
</dependency>
```
3.1. Creating the Model
This POJO represents a ride request:
```java
public class RideRequest {
    private String passengerId;
    private String location;
    private String destination;

    // default getters and setters
}
```
It won’t need special annotations for messaging.

4. Implementing a Publisher With DaprMessagingTemplate
The DaprMessagingTemplate is similar to other messaging templates from Spring, but doesn’t require a specific broker as a dependency. Let’s start by defining the name of our messaging component in our application.properties:

dapr.pubsub.name=ride-hailing
Then, we’ll use the DaprPubSubProperties class to reference this property and use our RideRequest as the message type. This completes the configuration needed to send messages:
```java
@Configuration
@EnableConfigurationProperties({ DaprPubSubProperties.class })
public class DaprMessagingConfig {

    @Bean
    public DaprMessagingTemplate<RideRequest> messagingTemplate(
      DaprClient client, DaprPubSubProperties config) {
        return new DaprMessagingTemplate<>(client, config.getName(), false);
    }
}
```
4.1. Receiving Messages With an Endpoint
Next, we’ll create a controller to receive ride requests and forward them to the “ride-requests” topic using the Dapr template. We can map the controller to any path we want:
```java
@RestController
@RequestMapping("/passenger")
public class PassengerRestController {

    @Autowired
    private DaprMessagingTemplate<RideRequest> messaging;

    @PostMapping("/request-ride")
    public String requestRide(@RequestBody RideRequest request) {
        messaging.send("ride-requests", request);
        return "waiting for drivers";
    }
}
```
Note that our message body doesn’t require any conversion or configuration, as Dapr will automatically handle it.

5. Creating and Configuring a Subscriber
In our example, drivers act as subscribers, receiving ride requests and deciding whether to accept them. We’ll implement this using Dapr’s @Topic annotation to bind incoming messages to a controller method.

5.1. Implementing the Controller With @Topic
When using the @Topic annotation, we must include both the component and topic names. The Dapr sidecar (automatically handled by the test container) calls this endpoint whenever it forwards a message from the broker:
```java
@RestController
@RequestMapping("driver")
public class DriverRestController {

    // ...

    @PostMapping("ride-request")
    @Topic(pubsubName = "ride-hailing", name = "ride-requests")
    public void onRideRequest(@RequestBody CloudEvent<RideRequest> event) {
        // ...
    }
}
```
Note that the payload is wrapped in a CloudEvent object, which Dapr automatically creates. This is helpful for advanced scenarios like routing or filtering based on CloudEvent‘s metadata, but not required for basic pub/sub.

5.2. Configuring Subscriber Behavior
Our subscriber represents a driver who accepts or declines the ride. To illustrate, we’ll use simple pattern logic to determine if the ride is acceptable. Let’s add this to our application.properties so we can easily change its value when launching our app:
```
driver.acceptance.criteria=East Side
```
Next, we’ll inject this value into a variable in our controller, along with variables to keep count of drives accepted/rejected:
```java
int drivesAccepted;
int drivesRejected;

@Value("${driver.acceptance.criteria}")
String criteria;

public int getDrivesAccepted() {
    return drivesAccepted;
}

public int getDrivesRejected() {
    return drivesRejected;
}
```
We’ll use these when writing our tests to check our controller behavior.

5.3. Handling the CloudEvent
Finally, we’ll retrieve our payload from the CloudEvent and decide if the drive is acceptable:
```java
@Topic(pubsubName = "ride-hailing", name = "ride-requests")
public void onRideRequest(@RequestBody CloudEvent<RideRequest> event) {
    RideRequest request = event.getData();

    if (request.getDestination().contains(criteria)) {
        drivesAccepted++;
    } else {
        drivesRejected++;
        throw new UnsupportedOperationException("drive rejected");
    }
}
```
Since we can’t directly reject a message, we throw an exception to trigger a requeue of the message. For RabbitMQ, this requires the requeueInFailure configuration, which we’ll set when creating our test container.

6. Testing the Publisher With Testcontainers
To verify that our publisher sends messages correctly, we’ll write integration tests using Testcontainers. This allows us to spin up a Dapr sidecar and a RabbitMQ instance without relying on external tools or the Dapr CLI.

6.1. Setting Up the Test Configuration
For our test properties, in addition to the acceptance criteria, we’ll include the messaging component name and an exclusive server port for the Dapr container.

Additionally, we’ll need to select a fixed port so that our components can locate each other within the same network:
```
driver.acceptance.criteria=East Side
dapr.pubsub.name=ride-hailing
server.port=60601
```
We’ll begin our configuration by setting the server port number and specifying a network to share between components. We’ll also include DaprPubSubProperties to obtain the name of our RabbitMQ component later:
```java
@TestConfiguration(proxyBeanMethods = false)
@EnableConfigurationProperties({ DaprPubSubProperties.class })
public class DaprTestContainersConfig {

    @Value("${server.port}")
    private int serverPort;

    @Bean
    public Network daprNetwork() {
        return Network.newNetwork();
    }

    // ...
}
```
6.2. Configuring the Containers
Let’s create our RabbitMQ container exposing the default port 5672:
```java
@Bean
public RabbitMQContainer rabbitMQContainer(Network daprNetwork) {
    return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"))
      .withExposedPorts(5672)
      .withNetworkAliases("rabbitmq")
      .withNetwork(daprNetwork);
}
```
Finally, we’ll add a Dapr container to wrap everything up, using the @ServiceConnection annotation to simplify the configuration:
```java
@Bean
@ServiceConnection
public DaprContainer daprContainer(
  Network daprNetwork, RabbitMQContainer rabbitMQ, DaprPubSubProperties pubSub) {
    Map<String, String> rabbitMqConfig = new HashMap<>();
    rabbitMqConfig.put("connectionString", "amqp://guest:guest@rabbitmq:5672");
    rabbitMqConfig.put("user", "guest");
    rabbitMqConfig.put("password", "guest");
    rabbitMqConfig.put("requeueInFailure", "true");

    return new DaprContainer("daprio/daprd:1.16.0")
      .withAppName("dapr-pubsub")
      .withNetwork(daprNetwork)
      .withComponent(new Component(pubSub.getName(), "pubsub.rabbitmq", "v1", rabbitMqConfig))
      .withAppPort(serverPort)
      .withAppChannelAddress("host.testcontainers.internal")
      .dependsOn(rabbitMQ);
}
```
Beyond the boilerplate, key configurations include:

requeueInFailure: We’ll enable this option since we cannot directly NACK messages. When we have multiple subscriber instances, this allows other clients to receive messages rejected by other clients.
withComponent(…”pubsub.rabbitmq”): We’ll use the RabbitMQ implementation, so we specify it here. Dapr supports many message brokers, including cloud provider-managed services like Google PubSub, Amazon SQS/SNS, and Azure Event Hub.
withAppChannelAddress: We’ll include this to enable host access to the container. Without it, tests may hang while waiting for Dapr responses.
We can also start the Dapr container with a logging configuration, making it easier to debug. For this, we set the withDaprLogLevel and withLogConsumer options:
```java
.withDaprLogLevel(DaprLogLevel.INFO) 
.withLogConsumer(outputFrame -> logger.info(outputFrame.getUtf8String()))
```
6.3. Creating the Test App
Now we’re ready to create our test app in our test package:
```java
@SpringBootApplication
public class DaprPublisherTestApp {

    public static void main(String[] args) {
        SpringApplication.from(DaprPublisherApp::main)
          .with(DaprTestContainersConfig.class)
          .run(args);
    }
}
```
We’ll reference our main application class to avoid duplicating any configuration, like the DaprMessagingConfig class. We’ll also need to copy our DriverRestController to our test folder for the integration tests.

6.4. Creating the Integration Test
We’ll need to reference our test app, configuration, and the DaprAutoConfiguration class. Then, inject our controller to check our control variables, and the Dapr container to know when our app is ready to receive messages:
```java
@SpringBootTest(
  classes = { 
    DaprPublisherTestApp.class, 
    DaprTestContainersConfig.class, 
    DaprAutoConfiguration.class }, 
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DaprPublisherIntegrationTest {

    @Autowired
    DriverRestController controller;

    @Autowired
    DaprContainer daprContainer;

    @Value("${server.port}")
    int serverPort;

    @Value("${driver.acceptance.criteria}")
    String criteria;

    // ...
}
```
Since we’ll need to verify whether our container has started correctly, we can wait for the “app is subscribed to the following topics” message. This helps ensure our tests only begin when our containers are ready to accept messages. We’ll also define the base URI of our API to make calls with RestAssured:
```java
@BeforeEach
void setUp() {
    RestAssured.baseURI = "http://localhost:" + serverPort;
    org.testcontainers.Testcontainers.exposeHostPorts(serverPort);

    Wait.forLogMessage(".*app is subscribed to the following topics.*", 1)
      .waitUntilReady(daprContainer);
}
```
Our first test involves publishing a drive request that meets the driver acceptance criteria and checking the number of accepted drives. When this number increases, we can assert that the subscriber processed the message:
```java
@Test
void whenDriveAcceptable_thenDrivesAcceptedIncrease() {
    int drivesAccepted = controller.getDrivesAccepted();

    given()
      .contentType(ContentType.JSON)
      .body("""
        {
          "passengerId": "1",
          "location": "Point A",
          "destination": "%s Point B"
        }
      """.formatted(criteria))
    .when()
      .post("/passenger/request-ride")
    .then()
      .statusCode(200);

    await()
      .atMost(Duration.ofSeconds(5))
      .until(controller::getDrivesAccepted, equalTo(drivesAccepted + 1));
}
```
Conversely, our second test involves publishing a drive request that our driver should reject:
```java
@Test
void whenDriveUnacceptable_thenDrivesRejectedIncrease() {
    int drivesRejected = controller.getDrivesRejected();

    given().contentType(ContentType.JSON)
      .body("""
        {
          "passengerId": "2",
          "location": "Point B",
          "destination": "West Side A"
        }
      """)
    .when()
      .post("/passenger/request-ride")
    .then()
      .statusCode(200);

    await()
      .atMost(Duration.ofSeconds(5))
      .until(controller::getDrivesRejected, greaterThan(drivesRejected));
}
```
This time, we test whether the number of drives rejected increased. Additionally, since messages are requeued on error, we verify that the variable is greater than its initial value, as we cannot be certain how many times it has been processed.

7. Testing the Subscriber With Testcontainers
Now let’s test our subscriber behavior. We’ll create a setup similar to the publisher, focusing on verifying how the subscriber processes incoming messages.

7.1. Setting Up the Environment
To start, we’ll include similar test properties, changing the server port only:
```
driver.acceptance.criteria=East Side
dapr.pubsub.name=ride-hailing
server.port=60602
```
We’ll copy the DaprMessagingConfig class to our test package so we can use it in our integration test. We’ll also need to copy the DaprTestContainersConfig to our test folder, as we’ll require the same containers.

7.2. Creating the Integration Test
Like our previous integration test, we’ll need to wire our container, controller, server port, driver acceptance criteria, and wait for the container to be ready during @Setup. We’ll also need to include the Dapr messaging template to send messages to our subscribers:
```java
@SpringBootTest(
  classes = { 
    DaprSubscriberTestApp.class, 
    DaprTestContainersConfig.class, 
    DaprMessagingConfig.class, 
    DaprAutoConfiguration.class }, 
  webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DaprSubscriberIntegrationTest {

    @Autowired
    DaprMessagingTemplate<RideRequest> messaging;

    @Autowired
    DriverRestController controller;

    @Autowired
    DaprContainer daprContainer;

    @Value("${server.port}")
    int serverPort;

    @Value("${driver.acceptance.criteria}")
    String criteria;

    // test setup...
}
```
7.3. Implementing the Test Scenarios
Our first test involves sending an acceptable drive and checking if our controller correctly received it:
```java
@Test
void whenDriveAcceptable_thenDrivesAcceptedIncrease() {
    int drivesAccepted = controller.getDrivesAccepted();

    RideRequest ride = new RideRequest(
      "1", "Point A", String.format("%s Point B", criteria));
    messaging.send("ride-requests", ride);

    await().atMost(Duration.ofSeconds(5))
      .until(controller::getDrivesAccepted, equalTo(drivesAccepted + 1));
}
```
Our second test consists of sending an unacceptable drive and checking if our controller correctly rejects it:
```java
@Test
void whenDriveUnacceptable_thenDrivesRejectedIncrease() {
    int drivesRejected = controller.getDrivesRejected();

    RideRequest request = new RideRequest("2", "Point B", "West Side Point A");
    messaging.send("ride-requests", request);

    await().atMost(Duration.ofSeconds(5))
      .until(controller::getDrivesRejected, greaterThan(drivesRejected));
}
```
With our subscriber tests in place, we’ve verified that Dapr correctly routes messages from the broker to our Spring Boot application and that the subscriber’s behavior works as expected.

8. Conclusion
In this article, we built a loosely coupled pub/sub messaging system using Spring Boot and Dapr. By leveraging Dapr’s abstraction over message brokers and its Spring Boot integration, we simplified our messaging logic without tying to a specific infrastructure. We also demonstrated how to run and test the entire setup locally using Testcontainers, enabling fast feedback loops during development.

As always, the source code is available over on GitHub.