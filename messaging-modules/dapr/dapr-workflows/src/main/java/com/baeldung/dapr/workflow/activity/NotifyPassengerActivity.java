package com.baeldung.dapr.workflow.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baeldung.dapr.workflow.model.NotificationInput;

import io.dapr.workflows.WorkflowActivity;
import io.dapr.workflows.WorkflowActivityContext;

@Component
public class NotifyPassengerActivity implements WorkflowActivity {

    private static final Logger logger = LoggerFactory.getLogger(NotifyPassengerActivity.class);

    @Override
    public Object run(WorkflowActivityContext ctx) {
        NotificationInput input = ctx.getInput(NotificationInput.class);
        logger.info("Notifying passenger: {}", input.getRequest().getRideRequest().getPassengerId());

        // In a real application, send notification via email, SMS, or push notification
        String message = String.format("Driver %s is on the way to %s. Estimated fare: $%.2f", 
            input.getRequest().getDriverId(), 
            input.getRequest().getRideRequest().getLocation(),
            input.getFare());

        logger.info("Notification sent: {}", message);
        return message;
    }
}
