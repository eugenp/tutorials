package com.baeldung.spring.di.constructor.model;

import lombok.Data;

@Data
public class Order {
    private Trade trade;
    private String transactionType;
    private String broker;

}
