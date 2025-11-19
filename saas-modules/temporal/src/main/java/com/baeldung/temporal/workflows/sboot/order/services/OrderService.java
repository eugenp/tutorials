package com.baeldung.temporal.workflows.sboot.order.services;

import com.baeldung.temporal.workflows.sboot.order.workflow.OrderWorkflow;
import com.baeldung.temporal.workflows.sboot.order.domain.OrderSpec;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private final WorkflowClient workflowClient;

    public OrderService(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    public OrderWorkflow getWorkflow(String orderExecutionId) {
        return workflowClient.newWorkflowStub(OrderWorkflow.class, orderExecutionId);
    }

    public String createOrderWorkflow(OrderSpec orderSpec) {
        var uuid = UUID.randomUUID();
        var wf = workflowClient.newWorkflowStub(
          OrderWorkflow.class,
          WorkflowOptions.newBuilder()
            .setTaskQueue("ORDERS")
            .setWorkflowId(uuid.toString()).build());
        var execution = WorkflowClient.start(wf::processOrder, orderSpec);
        return execution.getWorkflowId();

    }
}
