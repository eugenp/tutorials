package com.baeldung.mybatis.generatedid;

public class Car {

    private Long id;

    private String model;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }
}
