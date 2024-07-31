package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class ShippingRequest {
    private String orderId;
    private String deliveryAddress;
    private String deliveryInstructions;
}
