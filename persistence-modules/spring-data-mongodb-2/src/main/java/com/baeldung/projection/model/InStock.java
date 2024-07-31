package com.baeldung.projection.model;

import java.util.Objects;

public class InStock {

    private String wareHouse;
    private Integer quantity;

    public InStock(String wareHouse, int quantity) {
        this.wareHouse = wareHouse;
        this.quantity = quantity;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InStock inStock = (InStock) o;
        return Objects.equals(wareHouse, inStock.wareHouse) && Objects.equals(quantity, inStock.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wareHouse, quantity);
    }

}
