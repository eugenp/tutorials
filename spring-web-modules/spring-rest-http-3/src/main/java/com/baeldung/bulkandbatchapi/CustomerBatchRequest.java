package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.customer.Customer;
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

    public void setBatchType(BatchType batchType) {
        this.batchType = batchType;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
