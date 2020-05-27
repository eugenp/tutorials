package com.baeldung.paymentservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private String firstName;
    private String lastName;
    private String cardNumber;
    private double amount;
    private String currency;
}
