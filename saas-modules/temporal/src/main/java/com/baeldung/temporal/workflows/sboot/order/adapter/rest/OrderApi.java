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


    private OrderWorkflow getWorkflow(String orderExecutionId) {
        return workflowClient.newWorkflowStub(OrderWorkflow.class, orderExecutionId);
    }

    @PostMapping
    public ResponseEntity<OrderCreationResponse> createOrder(@RequestBody OrderSpec orderSpec) {
        var uuid = UUID.randomUUID();
        var wf = workflowClient.newWorkflowStub(
          OrderWorkflow.class,
          WorkflowOptions.newBuilder()
            .setTaskQueue("ORDERS")
            .setWorkflowId(uuid.toString()).build());
        var execution = WorkflowClient.start(wf::processOrder, orderSpec);
        var location = UriComponentsBuilder.fromUriString("/order/{orderExecutionId}").build(execution.getWorkflowId());

        return ResponseEntity.created(location).body(new OrderCreationResponse(uuid));

    }

    @GetMapping("/{orderExecutionId}")
    public ResponseEntity<Order> getOrder(@PathVariable("orderExecutionId") String orderExecutionId) {
        var wf = getWorkflow(orderExecutionId);
        return ResponseEntity.ok(wf.getOrder());
    }

    @GetMapping("/{orderExecutionId}/payment")
    public ResponseEntity<PaymentAuthorization> getPayment(@PathVariable("orderExecutionId") String orderExecutionId) {
        var wf = getWorkflow(orderExecutionId);
        var payment = wf.getPayment();
        if (payment == null) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(wf.getPayment());
        }
    }


    @GetMapping("/{orderExecutionId}/shipping")
    public ResponseEntity<Shipping> getOrderShipping(@PathVariable("orderExecutionId") String orderExecutionId) {
        var wf = getWorkflow(orderExecutionId);
        return ResponseEntity.ok(wf.getShipping());
    }

    @PutMapping("/{orderExecutionId}/paymentStatus")
    public ResponseEntity<Void> updatePaymentStatus(@PathVariable("orderExecutionId") String orderExecutionId, @RequestBody PaymentStatusUpdateInfo info) {
        var wf = getWorkflow(orderExecutionId);
        log.info("updatePaymentStatus: info={}", info.status());
        switch (info.status()) {
        case APPROVED -> wf.paymentAuthorized(info.transactionId(), info.authorizationId());
        case DECLINED -> wf.paymentDeclined(info.transactionId(), info.cause());
        default -> throw new IllegalArgumentException("Unsupported payment status: " + info.status());
        };

        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{orderExecutionId}/shippingStatus")
    public ResponseEntity<Void> updateShippingStatus(@PathVariable("orderExecutionId") String orderExecutionId, @RequestBody ShippingStatusUpdateInfo info) {
        var wf = getWorkflow(orderExecutionId);
        log.info("updateShippingStatus: info={}", info.status());
        switch (info.status()) {
        case RETURNED -> wf.packageReturned(info.statusTime());
        case SHIPPED -> wf.packagePickup(info.statusTime());
        case DELIVERED -> wf.packageDelivered(info.statusTime());
        default-> log.info("shipping status update: new status={}", info.status());
        }
        return ResponseEntity.accepted().build();
    }

    public record OrderCreationResponse(
      UUID orderExecutionId
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
