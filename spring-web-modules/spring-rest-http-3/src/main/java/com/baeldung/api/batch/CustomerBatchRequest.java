package com.baeldung.api.batch;

import java.util.Objects;
import java.util.Set;

public class CustomerBatchRequest {

    private BatchRequestType batchType;

    private Set<Customer> customerData;

    public BatchRequestType getBatchType() {
        return batchType;
    }

    public Set<Customer> getCustomerData() {
        return customerData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerBatchRequest that = (CustomerBatchRequest) o;
        return Objects.equals(batchType, that.batchType) && Objects.equals(customerData, that.customerData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchType, customerData);
    }
}
