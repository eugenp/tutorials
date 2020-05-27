package com.baeldung.paymentservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String paymentId;
    private String paymentMethod;
    private String customerFullName;
    private double amount;
    private String currency;

}

