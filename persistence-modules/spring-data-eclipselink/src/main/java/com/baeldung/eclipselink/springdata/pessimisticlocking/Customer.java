package com.baeldung.eclipselink.springdata.pessimisticlocking;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;

@Entity
public class Customer {

    @Id
    private Long customerId;
    private String name;
    private String lastName;
    @ElementCollection
    @CollectionTable(name = "customer_address")
    private List<Address> addressList;

    public Customer() {
    }

    public Customer(Long customerId, String name, String lastName, List<Address> addressList) {
        this.customerId = customerId;
        this.name = name;
        this.lastName = lastName;
        this.addressList = addressList;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
