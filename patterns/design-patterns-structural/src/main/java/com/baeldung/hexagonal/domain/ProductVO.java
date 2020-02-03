package com.baeldung.hexagonal.domain;

public class ProductVO implements IValueObject {
    private String name;

    public ProductVO(String name) {
        this.name = name;
    }

    //getters and setters...
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
