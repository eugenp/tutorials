package com.baeldung.jsonoptimization;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerSlimShortNames {

    @JsonProperty("i")
    private long id;

    @JsonProperty("n")
    private String name;

    @JsonProperty("a")
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomerSlimShortNames)) {
            return false;
        }
        CustomerSlimShortNames other = (CustomerSlimShortNames) obj;
        return Objects.equals(address, other.address) && id == other.id && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "CustomerSlim [id=" + id + ", name=" + name + ", address=" + address + "]";
    }

    public static CustomerSlimShortNames[] fromCustomers(Customer[] customers) {
        CustomerSlimShortNames[] feedback = new CustomerSlimShortNames[customers.length];

        for (int i = 0; i < customers.length; i++) {
            Customer aCustomer = customers[i];
            CustomerSlimShortNames newOne = new CustomerSlimShortNames();

            newOne.setId(aCustomer.getId());
            newOne.setName(aCustomer.getFirstName() + " " + aCustomer.getLastName());
            newOne.setAddress(aCustomer.getStreet() + ", " + aCustomer.getCity() + " " + aCustomer.getState() + " " + aCustomer.getPostalCode());

            feedback[i] = newOne;
        }

        return feedback;
    }

}
