package com.baeldung.bulkandbatchapi.customer;

import java.util.List;
import java.util.Objects;

public class CustomerBatchRequest {

    private BatchMethodType batchMethodType;

    private List<Customer> customerData;

    public BatchMethodType getBatchMethodType() {
        return batchMethodType;
    }

    public List<Customer> getCustomerData() {
        return customerData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerBatchRequest that = (CustomerBatchRequest) o;
        return Objects.equals(batchMethodType, that.batchMethodType) && Objects.equals(customerData, that.customerData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchMethodType, customerData);
    }
}
