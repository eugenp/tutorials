package com.baeldung.jsonoptimization;

import java.util.Objects;

public class CustomerSlim {
    private long id;
    private String name;
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
        if (!(obj instanceof CustomerSlim)) {
            return false;
        }
        CustomerSlim other = (CustomerSlim) obj;
        return Objects.equals(address, other.address) && id == other.id && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "CustomerSlim [id=" + id + ", name=" + name + ", address=" + address + "]";
    }

    public static CustomerSlim[] fromCustomers(Customer[] customers) {
        CustomerSlim[] feedback = new CustomerSlim[customers.length];

        for (int i = 0; i < customers.length; i++) {
            Customer aCustomer = customers[i];
            CustomerSlim newOne = new CustomerSlim();

            newOne.setId(aCustomer.getId());
            newOne.setName(aCustomer.getFirstName() + " " + aCustomer.getLastName());
            newOne.setAddress(aCustomer.getStreet() + ", " + aCustomer.getCity() + " " + aCustomer.getState() + " " + aCustomer.getPostalCode());

            feedback[i] = newOne;
        }

        return feedback;
    }

}
