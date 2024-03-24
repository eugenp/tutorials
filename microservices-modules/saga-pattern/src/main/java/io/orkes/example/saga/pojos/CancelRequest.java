package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class CancelRequest {
    private String orderId;
}
