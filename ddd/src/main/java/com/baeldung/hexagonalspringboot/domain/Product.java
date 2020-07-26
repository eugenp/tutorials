package com.baeldung.hexagonalspringboot.domain;

public class Product {

    private Long id;
    private String name;
    private String category;

    public Product(Long id, String category, String name) {
        this.id = id;
        this.category = category;
        this.name = name;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
