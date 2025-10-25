package com.baeldung.dapr.workflow;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.baeldung.dapr.workflow.activity.CalculateFareActivity;
import com.baeldung.dapr.workflow.activity.NotifyPassengerActivity;
import com.baeldung.dapr.workflow.activity.ValidateDriverActivity;
import com.baeldung.dapr.workflow.model.NotificationInput;
import com.baeldung.dapr.workflow.model.RideWorkflowRequest;
import com.baeldung.dapr.workflow.model.RideWorkflowStatus;

import io.dapr.workflows.Workflow;
import io.dapr.workflows.WorkflowStub;
import io.dapr.workflows.WorkflowTaskOptions;
import io.dapr.workflows.WorkflowTaskRetryPolicy;

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

            // Step 1: Validate the driver
            ctx.getLogger().info("Step 1: Validating driver {}", request.getDriverId());
            boolean isValid = ctx.callActivity(
                ValidateDriverActivity.class.getName(),
                request,
                options,
                boolean.class
            ).await();

            if (!isValid) {
                ctx.complete(new RideWorkflowStatus(request.getRideId(), "FAILED", "Driver validation failed"));
                return;
            }

            // Step 2: Calculate the fare
            ctx.getLogger().info("Step 2: Calculating fare");
            double fare = ctx.callActivity(
                CalculateFareActivity.class.getName(),
                request,
                options,
                double.class
            ).await();

            // Step 3: Notify the passenger
            ctx.getLogger().info("Step 3: Notifying passenger");
            NotificationInput notificationInput = new NotificationInput(request, fare);
            String notification = ctx.callActivity(
                NotifyPassengerActivity.class.getName(),
                notificationInput,
                options,
                String.class
            ).await();

            // Step 4: Wait for passenger confirmation
            ctx.getLogger().info("Step 4: Waiting for passenger confirmation");
            String confirmation = ctx.waitForExternalEvent(
                "passenger-confirmation",
                Duration.ofMinutes(5),
                String.class
            ).await();

            if (confirmation == null || !confirmation.equalsIgnoreCase("confirmed")) {
                ctx.complete(new RideWorkflowStatus(
                    request.getRideId(), 
                    "CANCELLED", 
                    "Passenger did not confirm the ride within the timeout period"
                ));
                return;
            }

            // Complete the workflow
            String message = String.format(
                "Ride confirmed and processed successfully. Fare: $%.2f. %s", 
                fare, 
                notification
            );
            RideWorkflowStatus status = new RideWorkflowStatus(request.getRideId(), "COMPLETED", message);

            ctx.getLogger().info("Workflow completed: {}", message);
            ctx.complete(status);
        };
    }
}
