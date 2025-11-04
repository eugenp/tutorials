package com.baeldung.temporal.workflows.sboot.order.adapter.rest;

import com.baeldung.temporal.workflows.sboot.order.OrderWorkflow;
import com.baeldung.temporal.workflows.sboot.order.domain.*;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderApi {

    private final WorkflowClient workflowClient;

    public OrderApi(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @PostMapping
    public ResponseEntity<OrderCreationResponse> createOrder(@RequestBody OrderSpec orderSpec, UriComponentsBuilder uriComponentsBuilder) {

        var uuid = UUID.randomUUID();
        var wf = workflowClient.newWorkflowStub(
          OrderWorkflow.class,
          WorkflowOptions.newBuilder()
            .setTaskQueue("ORDERS")
            .setWorkflowId(uuid.toString()).build());
        var execution = WorkflowClient.start(wf::processOrder, orderSpec);

        var location = UriComponentsBuilder.fromUriString("/order/{executionId}").build(execution.getWorkflowId());

        return ResponseEntity.created(location).body(new OrderCreationResponse(uuid));

    }

    @GetMapping("/{executionId}")
    public ResponseEntity<Order> getOrder(@PathVariable("executionId") String executionId) {
        var wf = workflowClient.newWorkflowStub(OrderWorkflow.class, executionId);
        return ResponseEntity.ok(wf.getOrder());
    }

    @GetMapping("/{executionId}/delivery")
    public ResponseEntity<Shipping> getOrderDelivery(@PathVariable("executionId") String executionId) {
        var wf = workflowClient.newWorkflowStub(OrderWorkflow.class, executionId);
        return ResponseEntity.ok(wf.getDelivery());
    }

    public record OrderCreationResponse(UUID orderId) {

    }

}
