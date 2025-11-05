package com.baeldung.spring.cloud.aws.sqs.conversion.model.event;

import java.time.LocalDate;
import java.util.UUID;

import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.Shipment;
import com.baeldung.spring.cloud.aws.sqs.conversion.model.entity.ShipmentStatus;

public class ShipmentRequestedEvent {

    private UUID orderId;
    private String customerAddress;
    private LocalDate shipBy;

    public ShipmentRequestedEvent() {
    }

    public ShipmentRequestedEvent(UUID orderId, String customerAddress, LocalDate shipBy) {
        this.orderId = orderId;
        this.customerAddress = customerAddress;
        this.shipBy = shipBy;
    }

    public Shipment toDomain() {
        return new Shipment(orderId, customerAddress, shipBy, ShipmentStatus.REQUESTED);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public LocalDate getShipBy() {
        return shipBy;
    }

    public void setShipBy(LocalDate shipBy) {
        this.shipBy = shipBy;
    }

}
