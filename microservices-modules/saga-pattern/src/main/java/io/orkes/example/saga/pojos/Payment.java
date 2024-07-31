package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class Payment {
    public enum Status {
        PENDING,
        FAILED,
        SUCCESSFUL,
        CANCELED
    }
    private String paymentId;
    private String orderId;
    private double amount;
    private PaymentMethod paymentMethod;
    private Status status;
    private long createdAt;
    private String errorMsg;
}
