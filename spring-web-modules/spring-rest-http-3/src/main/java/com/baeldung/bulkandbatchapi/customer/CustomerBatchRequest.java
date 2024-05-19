package com.baeldung.bulkandbatchapi.customer;

import java.util.List;

public class CustomerBatchRequest {

    private BatchOperationType batchOperationType;
    private List<Customer> customers;

    public BatchOperationType getBatchOperationType() {
        return batchOperationType;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
