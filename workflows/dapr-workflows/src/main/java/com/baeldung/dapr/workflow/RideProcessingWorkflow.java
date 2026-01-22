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
        return context -> {
            String instanceId = context.getInstanceId();
            context.getLogger()
                .info("Starting ride processing workflow: {}", instanceId);

            RideWorkflowRequest request = context.getInput(RideWorkflowRequest.class);

            WorkflowTaskOptions options = taskOptions();

            context.getLogger()
                .info("Step 1: Validating driver {}", request.getDriverId());
            boolean isValid = context.callActivity(ValidateDriverActivity.class.getName(), request, options, boolean.class)
                .await();

            if (!isValid) {
                context.complete(new RideWorkflowStatus(request.getRideId(), "FAILED", "Driver validation failed"));
                return;
            }

            context.getLogger()
                .info("Step 2: Calculating fare");
            double fare = context.callActivity(CalculateFareActivity.class.getName(), request, options, double.class)
                .await();

            context.getLogger()
                .info("Step 3: Notifying passenger");
            NotificationInput notificationInput = new NotificationInput(request, fare);
            String notification = context.callActivity(NotifyPassengerActivity.class.getName(), notificationInput, options, String.class)
                .await();

            context.getLogger()
                .info("Step 4: Waiting for passenger confirmation");
            String confirmation = context.waitForExternalEvent("passenger-confirmation", Duration.ofMinutes(5), String.class)
                .await();

            if (!"confirmed".equalsIgnoreCase(confirmation)) {
                context.complete(new RideWorkflowStatus(request.getRideId(), "CANCELLED", "Passenger did not confirm the ride within the timeout period"));
                return;
            }

            String message = String.format("Ride confirmed and processed successfully. Fare: $%.2f. %s", fare, notification);
            RideWorkflowStatus status = new RideWorkflowStatus(request.getRideId(), "COMPLETED", message);

            context.getLogger()
                .info("Workflow completed: {}", message);
            context.complete(status);
        };
    }

    private WorkflowTaskOptions taskOptions() {
        int maxRetries = 3;
        Duration backoffTimeout = Duration.ofSeconds(1);
        double backoffCoefficient = 1.5;
        Duration maxRetryInterval = Duration.ofSeconds(5);
        Duration maxTimeout = Duration.ofSeconds(10);

        WorkflowTaskRetryPolicy retryPolicy = new WorkflowTaskRetryPolicy(maxRetries, backoffTimeout, backoffCoefficient, maxRetryInterval, maxTimeout);
        return new WorkflowTaskOptions(retryPolicy);
    }
}
