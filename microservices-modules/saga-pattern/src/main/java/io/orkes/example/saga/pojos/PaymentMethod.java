package io.orkes.example.saga.pojos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PaymentMethod {
    private String type;
    private PaymentDetails details;
}
