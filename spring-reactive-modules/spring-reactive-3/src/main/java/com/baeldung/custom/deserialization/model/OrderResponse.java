package com.baeldung.custom.deserialization.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderResponse {

    public OrderResponse(UUID orderId, LocalDateTime orderDateTime, List<String> address, List<String> orderNotes) {
        this.orderId = orderId;
        this.orderDateTime = orderDateTime;
        this.address = address;
        this.orderNotes = orderNotes;
    }

    public OrderResponse() {
        this.orderId = null;
        this.orderDateTime = null;
        this.address = null;
        this.orderNotes = null;
    }

    private UUID orderId;

    private LocalDateTime orderDateTime;

    private List<String> address;

    private List<String> orderNotes;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDate(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<String> getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(List<String> orderNotes) {
        this.orderNotes = orderNotes;
    }

    @Override
    public String toString() {
        return "OrderResponse{" + "orderId=" + orderId + ", orderDateTime=" + orderDateTime + ", address=" + address + ", orderNotes=" + orderNotes + '}';
    }

}
