# Quickstart: Workflow
Get started with the Dapr Workflow building block

Note:
Redis is currently used as the state store component for Workflows in the Quickstarts. However, Redis does not support transaction rollbacks and should not be used in production as an actor state store.

Let’s take a look at the Dapr Workflow building block. In this Quickstart, you’ll create a simple console application to demonstrate Dapr’s workflow programming model and the workflow management APIs.

In this guide, you’ll:
* Run the order-processor application.
* Start the workflow and watch the workflow activites/tasks execute.
* Review the workflow logic and the workflow activities and how they’re represented in the code.

The workflow contains the following activities:
* NotifyActivity: Utilizes a logger to print out messages throughout the workflow.
* VerifyInventoryActivity: Checks the state store to ensure that there is enough inventory for the purchase.
* RequestApprovalActivity: Requests approval for orders over a certain cost threshold.
* ProcessPaymentActivity: Processes and authorizes the payment.
* UpdateInventoryActivity: Removes the requested items from the state store and updates the store with the new remaining inventory value.

The workflow also contains business logic:
* The workflow will not proceed with the payment if there is insufficient inventory.
* The workflow will call the RequestApprovalActivity and wait for an external approval event when the total cost of the order is greater than 5000.
* If the order is not approved or the approval is timed out, the workflow not proceed with the payment.

The order-processor console app starts and manages the lifecycle of an order processing workflow that stores and retrieves data in a state store. The workflow consists of four workflow activities, or tasks:

What happened?
When you ran dapr run -f .:

1. An OrderPayload is made containing one car.
2. A unique order ID for the workflow is generated (in the above example, d1bf548b-c854-44af-978e-90c61ed88e3c) and the workflow is scheduled.
3. The NotifyActivity workflow activity sends a notification saying an order for one car has been received.
4. The VertifyInventoryActivity workflow activity checks the inventory data, determines if you can supply the ordered item, and responds with the number of cars in stock. The inventory is sufficient so the workflow continues.
5. The total cost of the order is 5000, so the workflow will not call the RequestApprovalActivity activity.
6. The ProcessPaymentActivity workflow activity begins processing payment for order d1bf548b-c854-44af-978e-90c61ed88e3c and confirms if successful.
7. The UpdateInventoryActivity workflow activity updates the inventory with the current available cars after the order has been processed.
8. The NotifyActivity workflow activity sends a notification saying that order d1bf548b-c854-44af-978e-90c61ed88e3c has completed.
9. The workflow terminates as completed and the orderResult is set to processed.

## order-processor/WorkflowConsoleApp.java
In the application’s program file:
* The unique workflow order ID is generated
* The workflow is scheduled
* The workflow status is retrieved
* The workflow and the workflow activities it invokes are registered

```java
package io.dapr.quickstarts.workflows;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.quickstarts.workflows.activities.NotifyActivity;
import io.dapr.quickstarts.workflows.activities.ProcessPaymentActivity;
import io.dapr.quickstarts.workflows.activities.RequestApprovalActivity;
import io.dapr.quickstarts.workflows.activities.VerifyInventoryActivity;
import io.dapr.quickstarts.workflows.activities.UpdateInventoryActivity;
import io.dapr.quickstarts.workflows.models.InventoryItem;
import io.dapr.quickstarts.workflows.models.OrderPayload;
import io.dapr.workflows.client.DaprWorkflowClient;
import io.dapr.workflows.client.WorkflowInstanceStatus;
import io.dapr.workflows.runtime.WorkflowRuntime;
import io.dapr.workflows.runtime.WorkflowRuntimeBuilder;

public class WorkflowConsoleApp {

  private static final String STATE_STORE_NAME = "statestore";

  /**
   * The main method of this console app.
   *
   * @param args The port the app will listen on.
   * @throws Exception An Exception.
   */
  public static void main(String[] args) throws Exception {
    System.out.println("*** Welcome to the Dapr Workflow console app sample!");
    System.out.println("*** Using this app, you can place orders that start workflows.");
    // Wait for the sidecar to become available
    Thread.sleep(5 * 1000);

    // Register the OrderProcessingWorkflow and its activities with the builder.
    WorkflowRuntimeBuilder builder = new WorkflowRuntimeBuilder().registerWorkflow(OrderProcessingWorkflow.class);
    builder.registerActivity(NotifyActivity.class);
    builder.registerActivity(ProcessPaymentActivity.class);
    builder.registerActivity(RequestApprovalActivity.class);
    builder.registerActivity(VerifyInventoryActivity.class);
    builder.registerActivity(UpdateInventoryActivity.class);

    // Build and then start the workflow runtime pulling and executing tasks
    try (WorkflowRuntime runtime = builder.build()) {
      System.out.println("Start workflow runtime");
      runtime.start(false);
    }

    InventoryItem inventory = prepareInventoryAndOrder();

    DaprWorkflowClient workflowClient = new DaprWorkflowClient();
    try (workflowClient) {
      executeWorkflow(workflowClient, inventory);
    }

  }

  private static void executeWorkflow(DaprWorkflowClient workflowClient, InventoryItem inventory) {
    System.out.println("==========Begin the purchase of item:==========");
    String itemName = inventory.getName();
    int orderQuantity = inventory.getQuantity();
    int totalcost = orderQuantity * inventory.getPerItemCost();
    OrderPayload order = new OrderPayload();
    order.setItemName(itemName);
    order.setQuantity(orderQuantity);
    order.setTotalCost(totalcost);
    System.out.println("Starting order workflow, purchasing " + orderQuantity + " of " + itemName);

    String instanceId = workflowClient.scheduleNewWorkflow(OrderProcessingWorkflow.class, order);
    System.out.printf("scheduled new workflow instance of OrderProcessingWorkflow with instance ID: %s%n",
        instanceId);

    try {
      workflowClient.waitForInstanceStart(instanceId, Duration.ofSeconds(10), false);
      System.out.printf("workflow instance %s started%n", instanceId);
    } catch (TimeoutException e) {
      System.out.printf("workflow instance %s did not start within 10 seconds%n", instanceId);
      return;
    }

    try {
      WorkflowInstanceStatus workflowStatus = workflowClient.waitForInstanceCompletion(instanceId,
          Duration.ofSeconds(30),
          true);
      if (workflowStatus != null) {
        System.out.printf("workflow instance completed, out is: %s%n",
            workflowStatus.getSerializedOutput());
      } else {
        System.out.printf("workflow instance %s not found%n", instanceId);
      }
    } catch (TimeoutException e) {
      System.out.printf("workflow instance %s did not complete within 30 seconds%n", instanceId);
    }

  }

  private static InventoryItem prepareInventoryAndOrder() {
    // prepare 10 cars in inventory
    InventoryItem inventory = new InventoryItem();
    inventory.setName("cars");
    inventory.setPerItemCost(50000);
    inventory.setQuantity(10);
    DaprClient daprClient = new DaprClientBuilder().build();
    restockInventory(daprClient, inventory);

    // prepare order for 10 cars
    InventoryItem order = new InventoryItem();
    order.setName("cars");
    order.setPerItemCost(5000);
    order.setQuantity(1);
    return order;
  }

  private static void restockInventory(DaprClient daprClient, InventoryItem inventory) {
    String key = inventory.getName();
    daprClient.saveState(STATE_STORE_NAME, key, inventory).block();
  }
}
OrderProcessingWorkflow.java
In OrderProcessingWorkflow.java, the workflow is defined as a class with all of its associated tasks (determined by workflow activities).

package io.dapr.quickstarts.workflows;

import java.time.Duration;
import org.slf4j.Logger;

import io.dapr.quickstarts.workflows.activities.NotifyActivity;
import io.dapr.quickstarts.workflows.activities.ProcessPaymentActivity;
import io.dapr.quickstarts.workflows.activities.RequestApprovalActivity;
import io.dapr.quickstarts.workflows.activities.VerifyInventoryActivity;
import io.dapr.quickstarts.workflows.activities.UpdateInventoryActivity;
import io.dapr.quickstarts.workflows.models.ApprovalResponse;
import io.dapr.quickstarts.workflows.models.InventoryRequest;
import io.dapr.quickstarts.workflows.models.InventoryResult;
import io.dapr.quickstarts.workflows.models.Notification;
import io.dapr.quickstarts.workflows.models.OrderPayload;
import io.dapr.quickstarts.workflows.models.OrderResult;
import io.dapr.quickstarts.workflows.models.PaymentRequest;
import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;

public class OrderProcessingWorkflow extends Workflow {

  @Override
  public WorkflowStub create() {
    return ctx -> {
      Logger logger = ctx.getLogger();
      String orderId = ctx.getInstanceId();
      logger.info("Starting Workflow: " + ctx.getName());
      logger.info("Instance ID(order ID): " + orderId);
      logger.info("Current Orchestration Time: " + ctx.getCurrentInstant());

      OrderPayload order = ctx.getInput(OrderPayload.class);
      logger.info("Received Order: " + order.toString());
      OrderResult orderResult = new OrderResult();
      orderResult.setProcessed(false);

      // Notify the user that an order has come through
      Notification notification = new Notification();
      notification.setMessage("Received Order: " + order.toString());
      ctx.callActivity(NotifyActivity.class.getName(), notification).await();

      // Determine if there is enough of the item available for purchase by checking
      // the inventory
      InventoryRequest inventoryRequest = new InventoryRequest();
      inventoryRequest.setRequestId(orderId);
      inventoryRequest.setItemName(order.getItemName());
      inventoryRequest.setQuantity(order.getQuantity());
      InventoryResult inventoryResult = ctx.callActivity(VerifyInventoryActivity.class.getName(),
          inventoryRequest, InventoryResult.class).await();

      // If there is insufficient inventory, fail and let the user know
      if (!inventoryResult.isSuccess()) {
        notification.setMessage("Insufficient inventory for order : " + order.getItemName());
        ctx.callActivity(NotifyActivity.class.getName(), notification).await();
        ctx.complete(orderResult);
        return;
      }

      // Require orders over a certain threshold to be approved
      if (order.getTotalCost() > 5000) {
        ctx.callActivity(RequestApprovalActivity.class.getName(), order).await();

        ApprovalResponse approvalResponse = ctx.waitForExternalEvent("approvalEvent",
          Duration.ofSeconds(30), ApprovalResponse.class).await();
        if (!approvalResponse.isApproved()) {
          notification.setMessage("Order " + order.getItemName() + " was not approved.");
          ctx.callActivity(NotifyActivity.class.getName(), notification).await();
          ctx.complete(orderResult);
          return;
        }
      }

      // There is enough inventory available so the user can purchase the item(s).
      // Process their payment
      PaymentRequest paymentRequest = new PaymentRequest();
      paymentRequest.setRequestId(orderId);
      paymentRequest.setItemBeingPurchased(order.getItemName());
      paymentRequest.setQuantity(order.getQuantity());
      paymentRequest.setAmount(order.getTotalCost());
      boolean isOK = ctx.callActivity(ProcessPaymentActivity.class.getName(),
          paymentRequest, boolean.class).await();
      if (!isOK) {
        notification.setMessage("Payment failed for order : " + orderId);
        ctx.callActivity(NotifyActivity.class.getName(), notification).await();
        ctx.complete(orderResult);
        return;
      }

      inventoryResult = ctx.callActivity(UpdateInventoryActivity.class.getName(),
          inventoryRequest, InventoryResult.class).await();
      if (!inventoryResult.isSuccess()) {
        // If there is an error updating the inventory, refund the user
        // paymentRequest.setAmount(-1 * paymentRequest.getAmount());
        // ctx.callActivity(ProcessPaymentActivity.class.getName(),
        // paymentRequest).await();

        // Let users know their payment processing failed
        notification.setMessage("Order failed to update inventory! : " + orderId);
        ctx.callActivity(NotifyActivity.class.getName(), notification).await();
        ctx.complete(orderResult);
        return;
      }

      // Let user know their order was processed
      notification.setMessage("Order completed! : " + orderId);
      ctx.callActivity(NotifyActivity.class.getName(), notification).await();

      // Complete the workflow with order result is processed
      orderResult.setProcessed(true);
      ctx.complete(orderResult);
    };
  }
}
```
activities directory

The Activities directory holds the four workflow activities used by the workflow, defined in the following files:
NotifyActivity.java
RequestApprovalActivity
ReserveInventoryActivity
ProcessPaymentActivity
UpdateInventoryActivity