package io.orkes.example.saga.pojos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PaymentDetails {
    private String number;
    private String expiry;
    private int cvv;
}