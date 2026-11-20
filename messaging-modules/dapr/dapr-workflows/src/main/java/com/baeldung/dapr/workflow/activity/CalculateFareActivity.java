package com.baeldung.dapr.workflow.activity;

import org.springframework.stereotype.Component;

import com.baeldung.dapr.workflow.model.RideWorkflowRequest;

import io.dapr.workflows.WorkflowActivity;
import io.dapr.workflows.WorkflowActivityContext;

@Component
public class CalculateFareActivity implements WorkflowActivity {

    @Override
    public Object run(WorkflowActivityContext context) {
        RideWorkflowRequest request = context.getInput(RideWorkflowRequest.class);
        context.getLogger()
            .info("Calculating fare for ride: {}", request.getRideId());

        double baseFare = 5.0;
        double perMileFare = 2.5;
        double estimatedMiles = 10.0;

        double totalFare = baseFare + (perMileFare * estimatedMiles);
        context.getLogger()
            .info("Calculated fare: ${}", totalFare);

        return totalFare;
    }
}
