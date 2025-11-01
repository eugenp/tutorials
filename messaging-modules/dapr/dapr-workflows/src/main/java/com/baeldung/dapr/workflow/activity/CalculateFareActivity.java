package com.baeldung.dapr.workflow.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baeldung.dapr.workflow.model.RideWorkflowRequest;

import io.dapr.workflows.WorkflowActivity;
import io.dapr.workflows.WorkflowActivityContext;

@Component
public class CalculateFareActivity implements WorkflowActivity {

    private static final Logger logger = LoggerFactory.getLogger(CalculateFareActivity.class);

    @Override
    public Object run(WorkflowActivityContext context) {
        RideWorkflowRequest request = context.getInput(RideWorkflowRequest.class);
        logger.info("Calculating fare for ride: {}", request.getRideId());

        double baseFare = 5.0;
        double perMileFare = 2.5;
        double estimatedMiles = 10.0;

        double totalFare = baseFare + (perMileFare * estimatedMiles);
        logger.info("Calculated fare: ${}", totalFare);

        return totalFare;
    }
}
