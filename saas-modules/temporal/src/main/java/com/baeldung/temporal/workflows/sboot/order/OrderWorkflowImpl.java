package com.baeldung.temporal.workflows.sboot.order;

import com.baeldung.temporal.workflows.sboot.order.activities.OrderActivities;
import com.baeldung.temporal.workflows.sboot.order.domain.*;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@WorkflowImpl(taskQueues = "ORDERS")
public class OrderWorkflowImpl implements OrderWorkflow {


    private Order order;
    private Shipping shipping;

    public OrderWorkflowImpl() {
    }

    @Override
    public void processOrder(OrderSpec spec) {

        var activities = createActivitiesStub();

        // Reserve inventory
        activities.reserveOrderItems(spec.order());

        // Run delivery and payment in parallel



    }

    @Override
    public void paymentAuthorized(String transactionId) {

    }

    @Override
    public void paymentDeclined(String cause) {

    }

    @Override
    public void packagePickup(Instant pickupTime) {

    }

    @Override
    public void packageDelivered(Instant pickupTime) {

    }

    @Override
    public void packageReturned(Instant pickupTime) {

    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public Shipping getDelivery() {
        return null;
    }


    private OrderActivities createActivitiesStub() {
        return Workflow.newActivityStub(OrderActivities.class);
    }
}
