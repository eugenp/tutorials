package com.baeldung.dapr.workflow.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baeldung.dapr.workflow.model.RideWorkflowRequest;

import io.dapr.workflows.WorkflowActivity;
import io.dapr.workflows.WorkflowActivityContext;

@Component
public class ValidateDriverActivity implements WorkflowActivity {

    private static final Logger logger = LoggerFactory.getLogger(ValidateDriverActivity.class);

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
