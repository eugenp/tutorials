package com.baeldung.bulkandbatchapi.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CustomerBatchRequest {

    @NotNull
    private BatchType batchType;

    @NotNull
    @Size(min = 1, max = 20)
    private List<Customer> customers;

    public BatchType getBatchType() {
        return batchType;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
