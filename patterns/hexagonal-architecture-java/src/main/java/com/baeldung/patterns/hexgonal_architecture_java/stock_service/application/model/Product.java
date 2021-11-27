package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model;

import java.io.Serializable;

public class Product implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2099700543282079887L;
    private String code;
    private String name;
    private Long stock;

    public Product() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product [code=" + code + ", name=" + name + ", stock=" + stock + "]";
    }

}
