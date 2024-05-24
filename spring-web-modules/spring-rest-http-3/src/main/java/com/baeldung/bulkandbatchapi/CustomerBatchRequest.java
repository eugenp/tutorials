package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.customer.Customer;

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

    public void setBatchType(BatchType batchType) {
        this.batchType = batchType;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
