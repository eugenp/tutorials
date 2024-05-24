package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.customer.Customer;

import java.util.List;

public class CustomerBatchResponse {

    private BatchType batchType;
    private List<Customer> customers;
    private BatchStatus status;

    public BatchStatus getStatus() {
        return status;
    }

    public void setStatus(BatchStatus status) {
        this.status = status;
    }

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

    public static CustomerBatchResponse getCustomerBatchResponse(List<Customer> customers, BatchType batchType, BatchStatus batchStatus) {
        CustomerBatchResponse customerBatchResponse = new CustomerBatchResponse();
        customerBatchResponse.setBatchType(batchType);
        customerBatchResponse.setCustomers(customers);
        customerBatchResponse.setStatus(batchStatus);

        return customerBatchResponse;
    }
}
