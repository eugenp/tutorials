package com.baeldung.jdbc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Binesh on 12/3/2017.
 */
public class EmployeeAddress {
    private String id;

    @JsonIgnore
    private Employee employee;

    private String address;

    public EmployeeAddress(String id, String address) {
        this.id = id;
        this.address = address;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getAddress() {
        return address;
    }
}
