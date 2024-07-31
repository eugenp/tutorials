package com.baeldung.spring.data.jpa.paging;

public class Customer {

   private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
