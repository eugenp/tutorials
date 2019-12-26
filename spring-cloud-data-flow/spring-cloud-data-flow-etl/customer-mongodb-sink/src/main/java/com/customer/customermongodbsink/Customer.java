package com.customer.customermongodbsink;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}