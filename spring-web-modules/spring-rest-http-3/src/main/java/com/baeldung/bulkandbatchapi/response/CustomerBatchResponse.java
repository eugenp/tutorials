package com.baeldung.bulkandbatchapi.response;

import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.request.BatchType;

import java.util.List;

public class CustomerBatchResponse {

    private BatchType batchType;
    private List<Customer> customers;
    private BatchStatus status;

    public BatchType getBatchType() {
        return batchType;
    }

    public void setBatchType(BatchType batchType) {
        this.batchType = batchType;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public BatchStatus getStatus() {
        return status;
    }

    public void setStatus(BatchStatus status) {
        this.status = status;
    }

    public static CustomerBatchResponse getCustomerBatchResponse(List<Customer> customers, BatchType batchType, BatchStatus batchStatus) {
        CustomerBatchResponse customerBatchResponse = new CustomerBatchResponse();
        customerBatchResponse.setBatchType(batchType);
        customerBatchResponse.setCustomers(customers);
        customerBatchResponse.setStatus(batchStatus);

        return customerBatchResponse;
    }
}
