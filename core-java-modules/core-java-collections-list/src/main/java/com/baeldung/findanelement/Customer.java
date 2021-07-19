package com.baeldung.findanelement;

public class Customer {

    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return id * 20;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Customer) {
            Customer otherCustomer = (Customer) obj;
            if (id == otherCustomer.id)
                return true;
        }
        return false;
    }


}
