# Using Dapr Workflows with Spring Boot
Following the same approach that we used for Spring Data and Spring Messaging, the dapr-spring-boot-starter brings Dapr Workflow integration for Spring Boot users.

To work with Dapr Workflows you need to define and implement your workflows using code. The Dapr Spring Boot Starter makes your life easier by managing Workflows and WorkflowActivitys as Spring beans.

In order to enable the automatic bean discovery you can annotate your @SpringBootApplication with the @EnableDaprWorkflows annotation:
```java
@SpringBootApplication
@EnableDaprWorkflows
public class MySpringBootApplication {}
```
By adding this annotation, all the WorkflowActivitys will be automatically managed by Spring and registered to the workflow engine.

By having all WorkflowActivitys as managed beans we can use Spring @Autowired mechanism to inject any bean that our workflow activity might need to implement its functionality, for example the @RestTemplate:
```java
public class MyWorkflowActivity implements WorkflowActivity {

  @Autowired
  private RestTemplate restTemplate;
```
You can also @Autowired the DaprWorkflowClient to create new instances of your workflows.
```java
@Autowired
private DaprWorkflowClient daprWorkflowClient;
```
This enable applications to schedule new workflow instances and raise events.
```java
String instanceId = daprWorkflowClient.scheduleNewWorkflow(MyWorkflow.class, payload);
```
and
```java
daprWorkflowClient.raiseEvent(instanceId, "MyEvent", event);
```
Check the Dapr Workflow documentation for more information about how to work with Dapr Workflows: https://docs.dapr.io/developing-applications/building-blocks/workflow/workflow-overview/
