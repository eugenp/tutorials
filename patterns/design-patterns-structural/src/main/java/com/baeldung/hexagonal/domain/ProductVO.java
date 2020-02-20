package com.baeldung.hexagonal.domain;

public class ProductVO {
    private String name;

    public ProductVO(String name) {
        this.name = name;
    }

    //getters and setters...
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
