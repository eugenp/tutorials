package com.baeldung.dependency;

import org.springframework.beans.factory.annotation.Autowired;

public class Employee {

    @Autowired
    DepartmentDetails departmentDetails;

    AddressDetails addressDetails;

    PayDetails payDetails;

    @Autowired
    public Employee(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }

    @Autowired
    public void setPayDetails(PayDetails payDetails) {
        this.payDetails = payDetails;
    }

    public DepartmentDetails getDepartmentDetails() {
        return departmentDetails;
    }

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public PayDetails getPayDetails() {
        return payDetails;
    }

}
