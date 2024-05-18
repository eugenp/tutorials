package com.baeldung.api.bulkandbatch.customer;

import java.util.List;
import java.util.Objects;

public class CustomerBatchRequest {

    private BatchMethodType httpMethodType;

    private List<Customer> customerData;

    public BatchMethodType getHttpMethodType() {
        return httpMethodType;
    }

    public List<Customer> getCustomerData() {
        return customerData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerBatchRequest that = (CustomerBatchRequest) o;
        return Objects.equals(httpMethodType, that.httpMethodType) && Objects.equals(customerData, that.customerData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethodType, customerData);
    }
}
