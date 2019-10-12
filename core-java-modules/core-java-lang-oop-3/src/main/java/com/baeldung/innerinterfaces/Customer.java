package com.baeldung.innerinterfaces;

public class Customer {
    public interface List {
        void Add(Customer customer);

        String getCustomerNames();
    }

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
