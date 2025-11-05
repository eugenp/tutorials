package com.baeldung.synchronous.system.customer;

public class Customer {

    private Long id;
    private String name;

    private Customer() {
        super();
    }

    public Customer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
