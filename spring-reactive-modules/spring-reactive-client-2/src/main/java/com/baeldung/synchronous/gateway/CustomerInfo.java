package com.baeldung.synchronous.gateway;

public class CustomerInfo {

    private Long customerId;
    private String customerName;
    private Double balance;

    private CustomerInfo() {
        super();
    }

    public CustomerInfo(Long customerId, String customerName, Double balance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.balance = balance;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Double getBalance() {
        return balance;
    }
}
