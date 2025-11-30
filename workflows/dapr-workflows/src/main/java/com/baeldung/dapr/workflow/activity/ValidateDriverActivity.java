package com.baeldung.dapr.workflow.activity;

import org.springframework.stereotype.Component;

import com.baeldung.dapr.workflow.model.RideWorkflowRequest;

import io.dapr.workflows.WorkflowActivity;
import io.dapr.workflows.WorkflowActivityContext;

@Component
public class ValidateDriverActivity implements WorkflowActivity {

    @Override
    public Object run(WorkflowActivityContext context) {
        RideWorkflowRequest request = context.getInput(RideWorkflowRequest.class);
        context.getLogger()
            .info("Validating driver: {}", request.getDriverId());

        if (request.getDriverId() != null && !request.getDriverId()
            .isEmpty()) {
            context.getLogger()
                .info("Driver {} validated successfully", request.getDriverId());
            return true;
        }

        throw new IllegalArgumentException("Invalid driver ID");
    }
}
