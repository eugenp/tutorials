package com.baeldung.temporal.workflows.sboot.order.adapter.rest;

import com.baeldung.temporal.workflows.sboot.order.OrderWorkflow;
import com.baeldung.temporal.workflows.sboot.order.domain.*;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderApi {

    private static final Logger log = LoggerFactory.getLogger(OrderApi.class);

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

    @PutMapping("/{executionId}/paymentStatus")
    public ResponseEntity<Void> updatePaymentStatus(@PathVariable("executionId") String executionId, @RequestBody PaymentStatusUpdateInfo info) {
        var wf = workflowClient.newWorkflowStub(OrderWorkflow.class, executionId);
        switch (info.status()) {
        case APPROVED -> wf.paymentAuthorized(info.transactionId(), info.authorizationId());
        case DECLINED -> wf.paymentDeclined(info.transactionId(), info.cause());
        default -> throw new IllegalArgumentException("Unsupported payment status: " + info.status());
        };

        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{executionId}/shippingStatus")
    public ResponseEntity<Void> updateShippingStatus(@PathVariable("executionId") String executionId, @RequestBody ShippingStatusUpdateInfo info) {
        var wf = workflowClient.newWorkflowStub(OrderWorkflow.class, executionId);
        switch (info.status()) {
        case RETURNED -> wf.packageReturned(info.statusTime());
        case DELIVERED -> wf.packageDelivered(info.statusTime());
        default-> log.info("shipping status update: new status={}", info.status());
        }

        return ResponseEntity.accepted().build();
    }

    public record OrderCreationResponse(
      UUID orderId
    ) {};

    public record PaymentStatusUpdateInfo(
      PaymentStatus status,
      String authorizationId,
      String transactionId,
      String cause
    ){};

    public record ShippingStatusUpdateInfo(
      ShippingStatus status,
      Instant statusTime
    ) {};

}
