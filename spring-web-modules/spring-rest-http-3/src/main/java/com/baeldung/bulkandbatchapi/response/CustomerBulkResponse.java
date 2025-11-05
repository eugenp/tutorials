package com.baeldung.bulkandbatchapi.response;

import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.request.BulkActionType;

import java.util.List;

public class CustomerBulkResponse {

    private BulkActionType bulkActionType;
    private List<Customer> customers;
    private BulkStatus status;

    public CustomerBulkResponse() {
    }

    public CustomerBulkResponse(List<Customer> customers, BulkActionType bulkActionType, BulkStatus status) {
        this.customers = customers;
        this.bulkActionType = bulkActionType;
        this.status = status;
    }

    public BulkActionType getBulkType() {
        return bulkActionType;
    }

    public void setBulkType(BulkActionType bulkActionType) {
        this.bulkActionType = bulkActionType;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public BulkStatus getStatus() {
        return status;
    }

    public void setStatus(BulkStatus status) {
        this.status = status;
    }
}
