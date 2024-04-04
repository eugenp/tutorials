package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private int customerId;
    private float amount;
    private PaymentMethod method;
}
