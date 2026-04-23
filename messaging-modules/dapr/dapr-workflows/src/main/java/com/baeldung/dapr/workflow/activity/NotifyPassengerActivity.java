package com.baeldung.dapr.workflow.activity;

import org.springframework.stereotype.Component;

import com.baeldung.dapr.workflow.model.NotificationInput;

import io.dapr.workflows.WorkflowActivity;
import io.dapr.workflows.WorkflowActivityContext;

@Component
public class NotifyPassengerActivity implements WorkflowActivity {

    @Override
    public Object run(WorkflowActivityContext context) {
        NotificationInput input = context.getInput(NotificationInput.class);
        context.getLogger()
            .info("Notifying passenger: {}", input.request()
                .getRideRequest()
                .getPassengerId());

        String message = String.format("Driver %s is on the way to %s. Estimated fare: $%.2f", input.request()
            .getDriverId(),
            input.request()
                .getRideRequest()
                .getLocation(),
            input.fare());

        context.getLogger()
            .info("Notification sent: {}", message);
        return message;
    }
}
