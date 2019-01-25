package com.baeldung.interfaces;

/**
 * 内部接口的使用
 */
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
