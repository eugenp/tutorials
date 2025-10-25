# Dapr Workflows with PubSub

## 1. Introduction

In our previous article, we explored how to build a loosely coupled pub/sub messaging system using Spring Boot and Dapr. We created a ride-hailing application where passengers request rides and drivers subscribe to those requests. While this approach works well for simple message routing, real-world scenarios often require more sophisticated orchestration.

Consider what happens after a driver accepts a ride: we need to verify the driver's credentials, process payment, notify the passenger, update the ride status, and handle potential failures at each step. These multi-step processes require coordination, state management, and resilience—challenges that traditional pub/sub alone doesn't solve.

In this article, we'll extend our ride-hailing example by introducing Dapr Workflows. We'll learn how to orchestrate complex, long-running processes that react to both REST endpoints and pub/sub events, all while maintaining durability and reliability. We'll also demonstrate how to test these workflows using Testcontainers, ensuring our entire system works as expected without external infrastructure.

## 2. Understanding Durable Execution

Distributed systems frequently require coordinating multiple operations across different services. A payment processing workflow might involve validating an account, charging a credit card, updating an order database, and sending a confirmation email. If any step fails, we need to retry, compensate, or rollback previous actions.

Traditional approaches to this problem often involve:
- Manual state management in databases
- Complex retry logic scattered across services
- Custom error handling for each operation
- Difficulty tracking workflow progress

Durable execution addresses these challenges by automatically persisting workflow state at each step. If a process crashes or a service restarts, the workflow resumes from where it left off. This persistence happens transparently, allowing developers to write workflows as straightforward code while the runtime handles the complexity.

Dapr Workflows provides this durable execution model, along with built-in support for timeouts, retries, and compensation. By integrating with Dapr's other building blocks—like pub/sub, state management, and service invocation—workflows can orchestrate distributed operations without coupling to specific infrastructure.

## 3. Workflows as Code: Core Concepts

Dapr Workflows enables us to define orchestration logic using familiar programming constructs rather than XML or JSON definitions. The Spring Boot integration makes this even more seamless by managing workflows and activities as Spring beans.

### 3.1. The Workflow Abstraction

A workflow represents the orchestration logic—the sequence of steps that need to execute. In Dapr's Java SDK, we implement the `Workflow` interface and define our logic in the `create()` method:

```java
@Component
public class RideProcessingWorkflow implements Workflow {
    
    @Override
    public WorkflowStub create() {
        return ctx -> {
            // Orchestration logic goes here
            ctx.complete(result);
        };
    }
}
```

The workflow context (`ctx`) provides methods to call activities, wait for external events, and manage workflow state. Importantly, workflow code must be deterministic—it shouldn't directly perform I/O operations or generate random values. Instead, these non-deterministic operations belong in activities.

### 3.2. The WorkflowActivity Abstraction

Activities encapsulate individual units of work within a workflow. They represent the actual tasks that interact with external systems, databases, or services:

```java
@Component
public class ValidateDriverActivity implements WorkflowActivity {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public Object run(WorkflowActivityContext ctx) {
        String driverId = ctx.getInput(String.class);
        // Perform validation logic
        return validationResult;
    }
}
```

Since activities are Spring beans, we can leverage dependency injection to access other beans like `RestTemplate`, repositories, or any service we need. Activities can fail and retry, and the workflow runtime handles their execution and state management.

### 3.3. Enabling Workflow Auto-Discovery

The `@EnableDaprWorkflows` annotation simplifies workflow registration by automatically discovering all workflows and activities on the classpath:

```java
@SpringBootApplication
@EnableDaprWorkflows
public class RideWorkflowApp {
    public static void main(String[] args) {
        SpringApplication.run(RideWorkflowApp.class, args);
    }
}
```

With this annotation in place, Spring Boot automatically:
- Scans for classes implementing `Workflow` and `WorkflowActivity`
- Registers them as Spring beans
- Makes them available to the Dapr workflow runtime
- Enables dependency injection throughout the workflow infrastructure

This auto-discovery eliminates boilerplate configuration and follows Spring Boot's convention-over-configuration philosophy.

## 4. Extending the Ride-Hailing Example

Let's enhance our ride-hailing application to include a workflow that orchestrates the entire ride lifecycle. We'll create a new module that builds upon the existing publisher and subscriber code.

### 4.1. The Scenario

When a driver accepts a ride request (via our pub/sub subscriber), we want to:
1. Validate the driver's credentials
2. Calculate and process the estimated fare
3. Notify the passenger that a driver is en route
4. Update the ride status to "in progress"

This workflow should start when we receive a "driver accepted" event from our pub/sub system. Each step needs proper error handling, and the entire process must be durable—if our service restarts, the workflow should resume seamlessly.

### 4.2. Project Setup

We'll add the same dependencies we used in our pub/sub modules, ensuring we only rely on `dapr-spring-boot-starter` and the test dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>io.dapr.spring</groupId>
    <artifactId>dapr-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>io.dapr.spring</groupId>
    <artifactId>dapr-spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 4.3. Defining the Domain Models

We'll extend our `RideRequest` model to include workflow-specific information:

```java
public class RideWorkflowRequest {
    private String rideId;
    private RideRequest rideRequest;
    private String driverId;
    private String workflowInstanceId;
    
    // constructors, getters, and setters
}
```

We'll also create a simple model to track workflow progress:

```java
public class RideWorkflowStatus {
    private String rideId;
    private String status;
    private String message;
    
    // constructors, getters, and setters
}
```

### 4.4. Implementing Workflow Activities

Let's create activities for each step in our workflow. First, the driver validation activity:

```java
@Component
public class ValidateDriverActivity implements WorkflowActivity {
    
        private static final Logger logger = LoggerFactory.getLogger(
            ValidateDriverActivity.class);
    
    @Override
    public Object run(WorkflowActivityContext ctx) {
        RideWorkflowRequest request = ctx.getInput(RideWorkflowRequest.class);
        logger.info("Validating driver: {}", request.getDriverId());
        
        // Simulate validation logic
        if (request.getDriverId() != null && !request.getDriverId().isEmpty()) {
            logger.info("Driver {} validated successfully", request.getDriverId());
            return true;
        }
        
        throw new IllegalArgumentException("Invalid driver ID");
    }
}
```

Next, we'll create an activity to calculate the fare:

```java
@Component
public class CalculateFareActivity implements WorkflowActivity {
    
        private static final Logger logger = LoggerFactory.getLogger(
            CalculateFareActivity.class);
    
    @Override
    public Object run(WorkflowActivityContext ctx) {
        RideWorkflowRequest request = ctx.getInput(RideWorkflowRequest.class);
        logger.info("Calculating fare for ride: {}", request.getRideId());
        
        // Simulate fare calculation
        double baseFare = 5.0;
        double perMileFare = 2.5;
        double estimatedMiles = 10.0; // In reality, calculate from location/destination
        
        double totalFare = baseFare + (perMileFare * estimatedMiles);
        logger.info("Calculated fare: ${}", totalFare);
        
```
// ...
```java
        return totalFare;
    }
}
```

Finally, let's create an activity to notify the passenger:

```java
@Component
public class NotifyPassengerActivity implements WorkflowActivity {
    
        private static final Logger logger = LoggerFactory.getLogger(
            NotifyPassengerActivity.class);
    
    @Override
    public Object run(WorkflowActivityContext ctx) {
        RideWorkflowRequest request = ctx.getInput(RideWorkflowRequest.class);
        logger.info("Notifying passenger: {}", request.getRideRequest().getPassengerId());
        
        // In a real application, send notification via email, SMS, or push notification
        String message = String.format("Driver %s is on the way to %s", 
            request.getDriverId(), 
            request.getRideRequest().getLocation());
        
        logger.info("Notification sent: {}", message);
        return message;
    }
}
```

### 4.5. Implementing the Workflow

Now we can implement our ride processing workflow that orchestrates these activities:

```java
@Component
public class RideProcessingWorkflow implements Workflow {

    @Override
    public WorkflowStub create() {
        return ctx -> {
            String instanceId = ctx.getInstanceId();
            ctx.getLogger().info("Starting ride processing workflow: {}", instanceId);

            RideWorkflowRequest request = ctx.getInput(RideWorkflowRequest.class);

            // Configure retry policy for activities
            Duration backoffTimeout = Duration.ofSeconds(1);
            Duration maxTimeout = Duration.ofSeconds(10);
            WorkflowTaskRetryPolicy retryPolicy = new WorkflowTaskRetryPolicy(
                3, backoffTimeout, 1.5, Duration.ofSeconds(5), maxTimeout);
            WorkflowTaskOptions options = new WorkflowTaskOptions(retryPolicy);

```
// ...
```java
            // Step 1: Validate the driver
            ctx.getLogger().info("Step 1: Validating driver {}", request.getDriverId());
            boolean isValid = ctx.callActivity(
                ValidateDriverActivity.class.getName(),
                request,
                options,
                boolean.class
            ).await();

            if (!isValid) {
                            ctx.complete(new RideWorkflowStatus(
                                request.getRideId(), "FAILED", "Driver validation failed"));
                return;
            }
        };
    }
}
```

// ... continued

```java
@Component
public class RideProcessingWorkflow_Part2 {
    // continuation of the workflow example (for readability in the article)
    // Step 2 and 3 shown below

    // Step 2: Calculate the fare
    // (this snippet continues the logic from the previous block)
    double fare = ctx.callActivity(
        CalculateFareActivity.class.getName(),
        request,
        options,
        double.class
    ).await();

    // Step 3: Notify the passenger
    String notification = ctx.callActivity(
        NotifyPassengerActivity.class.getName(),
        request,
```
// ...
```java
        options,
        String.class
    ).await();

    // Complete the workflow
        String message = String.format(
            "Ride processed successfully. Fare: $%.2f. %s", fare, notification);
        RideWorkflowStatus status = new RideWorkflowStatus(
            request.getRideId(), "COMPLETED", message);

    ctx.getLogger().info("Workflow completed: {}", message);
    ctx.complete(status);
}
```

Notice how we configure a retry policy for our activities. This ensures that transient failures—like network issues or temporary service unavailability—don't cause the entire workflow to fail. The workflow will automatically retry activities according to the policy.

## 5. Integrating PubSub with Workflows

Now comes the exciting part: connecting our pub/sub system to the workflow engine. We'll create a controller that starts workflows in response to REST calls and raises workflow events when receiving pub/sub messages.

### 5.1. Starting Workflows via REST

First, let's create a REST endpoint to manually start a workflow:

```java
@RestController
@RequestMapping("/workflow")
@EnableDaprWorkflows
public class RideWorkflowController {
    
    private static final Logger logger = LoggerFactory.getLogger(RideWorkflowController.class);
    
    @Autowired
    private DaprWorkflowClient workflowClient;
    
    @PostMapping("/start-ride")
    public RideWorkflowRequest startRideWorkflow(@RequestBody RideWorkflowRequest request) {
        logger.info("Starting workflow for ride: {}", request.getRideId());
        
        String instanceId = workflowClient.scheduleNewWorkflow(
            RideProcessingWorkflow.class, request);
        
        request.setWorkflowInstanceId(instanceId);
```
// ...
```java
        logger.info("Workflow started with instance ID: {}", instanceId);
        
        return request;
    }
    
    @GetMapping("/status/{instanceId}")
    public WorkflowInstanceStatus getWorkflowStatus(@PathVariable String instanceId) {
        return workflowClient.getInstanceState(instanceId, true).block();
    }
}
```

// ... continued

```java
@RestController
@RequestMapping("/workflow")
@EnableDaprWorkflows
public class RideWorkflowController_Part2 {
    // continuation of the controller example (status endpoint shown)

    private static final Logger logger = LoggerFactory.getLogger(RideWorkflowController_Part2.class);

    @Autowired
    private DaprWorkflowClient workflowClient;

    @GetMapping("/status/{instanceId}")
    public WorkflowInstanceStatus getWorkflowStatus(@PathVariable String instanceId) {
        return workflowClient.getInstanceState(instanceId, true).block();
    }
}
```

The `DaprWorkflowClient` allows us to schedule new workflow instances and query their status. Each workflow gets a unique instance ID that we can use to track its progress.

### 5.2. Triggering Workflows from PubSub Events

Now let's create a subscriber that listens for ride acceptance events and starts workflows:

```java
@RestController
@RequestMapping("/workflow-subscriber")
public class WorkflowEventSubscriber {
    
    private static final Logger logger = LoggerFactory.getLogger(WorkflowEventSubscriber.class);
    public static final String DRIVER_ACCEPTANCE_TOPIC = "driver-acceptance";
    
    @Autowired
    private DaprWorkflowClient workflowClient;
    
    @PostMapping("/driver-accepted")
    @Topic(pubsubName = "ride-hailing", name = DRIVER_ACCEPTANCE_TOPIC)
    public void onDriverAcceptance(@RequestBody CloudEvent<RideWorkflowRequest> event) {
        RideWorkflowRequest request = event.getData();
        logger.info("Received driver acceptance event for ride: {}", request.getRideId());
        
        // Start the ride processing workflow
        String instanceId = workflowClient.scheduleNewWorkflow(
```
// ...
```java
            RideProcessingWorkflow.class, request);
        
        logger.info("Started workflow {} for accepted ride {}", instanceId, request.getRideId());
    }
}
```

// ... continued

```java
@RestController
@RequestMapping("/workflow-subscriber")
public class WorkflowEventSubscriber_Part2 {
    // continuation (showing the subscription annotation and method signature separately)

    private static final Logger logger = LoggerFactory.getLogger(WorkflowEventSubscriber_Part2.class);
    public static final String DRIVER_ACCEPTANCE_TOPIC = "driver-acceptance";

    @Autowired
    private DaprWorkflowClient workflowClient;

    @PostMapping("/driver-accepted")
    @Topic(pubsubName = "ride-hailing", name = DRIVER_ACCEPTANCE_TOPIC)
    public void onDriverAcceptance(@RequestBody CloudEvent<RideWorkflowRequest> event) {
        RideWorkflowRequest request = event.getData();
        logger.info("Received driver acceptance event for ride: {}", request.getRideId());

        // Start the ride processing workflow
```
// ...
```java
        String instanceId = workflowClient.scheduleNewWorkflow(
            RideProcessingWorkflow.class, request);

        logger.info("Started workflow {} for accepted ride {}", instanceId, request.getRideId());
    }
}
```

This approach decouples our workflow orchestration from the pub/sub messaging system. Drivers accept rides through pub/sub events, but the complex multi-step processing happens in a durable workflow.

### 5.3. Configuration

We'll need to configure our application properties:

```properties
dapr.pubsub.name=ride-hailing
server.port=60603
```

## 6. Testing Workflows with Testcontainers

Testing workflows is crucial to ensure our orchestration logic works correctly. The `dapr-spring-boot-starter-test` module integrates seamlessly with Testcontainers, allowing us to test the complete system.

### 6.1. Test Configuration

Let's create our test configuration that sets up Dapr and RabbitMQ containers:

```java
@TestConfiguration(proxyBeanMethods = false)
@EnableConfigurationProperties({ DaprPubSubProperties.class })
public class DaprWorkflowTestConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(DaprWorkflowTestConfig.class);
    
    @Value("${server.port}")
    private int serverPort;
    
    @Bean
    public Network daprNetwork() {
        return Network.newNetwork();
    }
    
    @Bean
    public RabbitMQContainer rabbitMQContainer(Network daprNetwork) {
        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"))
            .withExposedPorts(5672)
```
// ...
```java
            .withNetworkAliases("rabbitmq")
            .withNetwork(daprNetwork);
    }
}
```

// ... continued

```java
@TestConfiguration(proxyBeanMethods = false)
public class DaprWorkflowTestConfig_Part2 {
    
    @Bean
    @ServiceConnection
    public DaprContainer daprContainer(
        Network daprNetwork, 
        RabbitMQContainer rabbitMQ, 
        DaprPubSubProperties pubSub) {
        
        Map<String, String> rabbitMqConfig = new HashMap<>();
        rabbitMqConfig.put("connectionString", "amqp://guest:guest@rabbitmq:5672");
        rabbitMqConfig.put("user", "guest");
        rabbitMqConfig.put("password", "guest");
        
        return new DaprContainer("daprio/daprd:1.16.0")
            .withAppName("dapr-workflows")
            .withNetwork(daprNetwork)
```
// ...
```java
            .withComponent(new Component(pubSub.getName(), "pubsub.rabbitmq", "v1", rabbitMqConfig))
            .withAppPort(serverPort)
            .withAppChannelAddress("host.testcontainers.internal")
            .withDaprLogLevel(DaprLogLevel.INFO)
            .withLogConsumer(outputFrame -> logger.info(outputFrame.getUtf8String()))
            .dependsOn(rabbitMQ);
    }
}
```

### 6.2. Writing Integration Tests

Now let's create integration tests that verify our workflow executes correctly:

```java
@SpringBootTest(
    classes = { 
        DaprWorkflowApp.class, 
        DaprWorkflowTestConfig.class,
        DaprAutoConfiguration.class 
    },
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RideWorkflowIntegrationTest {
    
    @Autowired
    private DaprContainer daprContainer;
    
    @Autowired
    private DaprWorkflowClient workflowClient;
    
    @Value("${server.port}")
    private int serverPort;
    
```
// ...
```java
    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + serverPort;
        org.testcontainers.Testcontainers.exposeHostPorts(serverPort);
        
        Wait.forLogMessage(".*app is subscribed to the following topics.*", 1)
            .waitUntilReady(daprContainer);
    }
}
```

// ... continued

```java
// Test: start workflow via REST and await completion
class RideWorkflowIntegrationTest_Part2 {

    @Test
    void whenWorkflowStarted_thenAllActivitiesExecute() {
        RideRequest rideRequest = new RideRequest("passenger-1", "Downtown", "Airport");
        RideWorkflowRequest workflowRequest = new RideWorkflowRequest(
            "ride-123", rideRequest, "driver-456", null);

        // Start the workflow via REST
        RideWorkflowRequest response = given()
            .contentType(ContentType.JSON)
            .body(workflowRequest)
        .when()
            .post("/workflow/start-ride")
        .then()
            .statusCode(200)
            .extract()
```
// ...
```java
            .as(RideWorkflowRequest.class);

        String instanceId = response.getWorkflowInstanceId();
        assertNotNull(instanceId);

        // Wait for workflow to complete
        await()
            .atMost(Duration.ofSeconds(15))
            .pollInterval(Duration.ofMillis(500))
            .until(() -> {
                WorkflowInstanceStatus status = workflowClient.getInstanceState(instanceId, false);
                return status != null && status.getRuntimeStatus() == WorkflowInstanceStatus.WorkflowRuntimeStatus.COMPLETED;
            });

        // Verify the workflow completed successfully
        WorkflowInstanceStatus finalStatus = workflowClient.getInstanceState(instanceId, true);
        assertEquals(WorkflowInstanceStatus.WorkflowRuntimeStatus.COMPLETED, finalStatus.getRuntimeStatus());
    }
```
// ...
```java
}
```

// ... continued

```java
// Test: trigger workflow from PubSub event (part 1)
class RideWorkflowIntegrationTest_Part3 {

    @Test
    void whenPubSubEventReceived_thenWorkflowStarts_Part1() {
        RideRequest rideRequest = new RideRequest("passenger-2", "Mall", "Home");
        RideWorkflowRequest workflowRequest = new RideWorkflowRequest(
            "ride-456", rideRequest, "driver-789", null);

        // Publish event to trigger workflow
        CloudEvent<RideWorkflowRequest> cloudEvent = new CloudEvent<>();
        cloudEvent.setData(workflowRequest);

        given()
            .contentType(ContentType.JSON)
            .body(cloudEvent)
        .when()
            .post("/workflow-subscriber/driver-accepted")
```
// ...
```java
        .then()
            .statusCode(200);
    }
}

// ... continued

```java
// Test: trigger workflow from PubSub event (part 2)
class RideWorkflowIntegrationTest_Part3_Part2 {

    @Test
    void whenPubSubEventReceived_thenWorkflowStarts_Part2() {
        // Wait for workflow to be created and complete
        await()
            .atMost(Duration.ofSeconds(15))
            .pollInterval(Duration.ofMillis(500))
            .until(() ->
```
// ...
```java
                workflowClient.listInstances(
                    WorkflowInstanceStatus.WorkflowRuntimeStatus.COMPLETED, null)
                    .stream()
                    .anyMatch(status -> status.getRuntimeStatus() == WorkflowInstanceStatus.WorkflowRuntimeStatus.COMPLETED)
            );
    }
}
```

These tests demonstrate two key scenarios:
1. Starting a workflow directly via REST endpoint
2. Triggering a workflow through a pub/sub event

We use Awaitility to wait for asynchronous workflow execution, ensuring our tests don't suffer from timing issues. The `DaprWorkflowClient` allows us to query workflow status and verify completion.

### 6.3. Testing Activity Retry Behavior

Let's also test that our retry policy works correctly when activities fail:

```java
@Test
void whenActivityFails_thenRetryPolicyApplies_Part1() {
    // Create a request that will cause validation to fail initially
    RideWorkflowRequest workflowRequest = new RideWorkflowRequest(
        "ride-789", new RideRequest("passenger-3", "Park", "Beach"), "", null);
    
    String instanceId = workflowClient.scheduleNewWorkflow(
        RideProcessingWorkflow.class, workflowRequest);
}
```

// ... continued

```java
@Test
void whenActivityFails_thenRetryPolicyApplies_Part2() {
    // Wait and verify the workflow eventually fails after retries
    await()
        .atMost(Duration.ofSeconds(20))
        .pollInterval(Duration.ofMillis(500))
        .until(() -> {
            WorkflowInstanceStatus status = workflowClient.getInstanceState(instanceId, false);
            return status != null && status.getRuntimeStatus() == WorkflowRuntimeStatus.FAILED;
        });
    
    WorkflowInstanceStatus finalStatus = workflowClient.getInstanceState(instanceId, true);
    assertEquals(WorkflowRuntimeStatus.FAILED, finalStatus.getRuntimeStatus());
}
```

This test verifies that when an activity fails (in this case, validation fails due to an empty driver ID), the workflow respects the retry policy before eventually failing.

## 7. Workflow Patterns and Best Practices

Dapr Workflows supports several common orchestration patterns that solve real-world problems:

### 7.1. Common Patterns

**Fan-out/Fan-in**: Execute multiple activities in parallel and wait for all to complete:
```java
Task<Boolean> validation1 = ctx.callActivity(ValidateActivity1.class.getName(), input, boolean.class);
Task<Boolean> validation2 = ctx.callActivity(ValidateActivity2.class.getName(), input, boolean.class);

boolean result1 = validation1.await();
boolean result2 = validation2.await();
```

**Human Approval**: Wait for external events before continuing:
```java
String approval = ctx.waitForExternalEvent("approval-event", String.class).await();
```

**Saga Pattern**: Compensate for failures by undoing previous steps:
```java
try {
    ctx.callActivity(ChargePaymentActivity.class.getName(), request).await();
} catch (Exception e) {
    ctx.callActivity(RefundPaymentActivity.class.getName(), request).await();
    throw e;
}
```

### 7.2. Design Guidelines

When designing workflows, follow these best practices:

**Keep Workflows Deterministic**: Don't perform I/O operations, access databases, or generate random values directly in workflow code. Use activities for these operations.

**Configure Appropriate Timeouts**: Set realistic timeouts for activities and workflows to prevent them from running indefinitely:
```java
WorkflowTaskRetryPolicy retryPolicy = new WorkflowTaskRetryPolicy(
    maxNumberOfAttempts,
    firstRetryInterval,
    backoffCoefficient,
    maxRetryInterval,
    retryTimeout
);
```

**Handle Failures Gracefully**: Consider what should happen when activities fail. Should the workflow retry, compensate, or fail immediately?

**Monitor Workflow Execution**: Use logging and workflow status queries to understand what's happening in your system.

**Test Thoroughly**: Integration tests with Testcontainers give you confidence that your workflows behave correctly in realistic scenarios.

### 7.3. Additional Resources

For more workflow patterns and examples, explore:
- The [Diagrid Labs workflow patterns repository](https://github.com/diagrid-labs/workflow-patterns-spring-boot) showcases various orchestration patterns
- The [Dapr Java SDK workflows examples](https://github.com/dapr/java-sdk/tree/master/spring-boot-examples/workflows) provide additional implementation guidance
- The [official Dapr Workflow documentation](https://docs.dapr.io/developing-applications/building-blocks/workflow/workflow-overview/) covers concepts and features in depth

## 8. Conclusion

In this article, we've extended our Dapr pub/sub example to include durable workflow orchestration. By combining Dapr PubSub with Dapr Workflows, we created a system that handles both event-driven messaging and complex multi-step processes.

We learned how to:
- Implement workflows and activities as Spring Boot components
- Use `@EnableDaprWorkflows` for automatic registration
- Start workflows from REST endpoints and pub/sub events
- Configure retry policies for resilient execution
- Test workflows comprehensively using Testcontainers

This combination of pub/sub and workflows provides a powerful foundation for building distributed applications. Events drive real-time communication between services, while workflows orchestrate complex operations that span multiple steps and services.

The Dapr approach keeps our code infrastructure-agnostic—we can swap message brokers or state stores without changing application logic. Testing with Testcontainers ensures we catch integration issues early, all without requiring manual infrastructure setup or the Dapr CLI.

As always, the source code is available over on GitHub.
