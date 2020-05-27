package com.baeldung.paymentservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse{

    private String paymentId;
    private String paymentMethod;
    private String customerFullName;
    private Double amount;
    private String currency;

}