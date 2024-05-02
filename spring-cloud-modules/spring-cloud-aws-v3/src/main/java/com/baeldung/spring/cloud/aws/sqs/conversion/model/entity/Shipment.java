package com.baeldung.spring.cloud.aws.sqs.conversion.model.entity;

import java.time.LocalDate;
import java.util.UUID;

public class Shipment {

    private UUID orderId;
    private String customerAddress;
    private LocalDate shipBy;
    private ShipmentStatus status;

    public Shipment(){}

    public Shipment(UUID orderId, String customerAddress, LocalDate shipBy, ShipmentStatus status) {
        this.orderId = orderId;
        this.customerAddress = customerAddress;
        this.shipBy = shipBy;
        this.status = status;
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

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }
}
