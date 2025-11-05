package com.baeldung.bulkandbatchapi.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CustomerBulkRequest {

    @NotNull
    private BulkActionType bulkActionType;

    @NotNull
    @Size(min = 1, max = 20)
    private List<Customer> customers;

    public BulkActionType getBulkActionType() {
        return bulkActionType;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
