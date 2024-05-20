package com.baeldung.bulkandbatchapi.customer;

import java.util.List;

public class CustomerBatchRequest {

    private BatchType batchType;
    private List<Customer> customers;
    public BatchType getBatchType() {
        return batchType;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
