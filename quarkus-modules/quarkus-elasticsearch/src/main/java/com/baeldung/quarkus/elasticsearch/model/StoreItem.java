package com.baeldung.quarkus.elasticsearch.model;

import java.util.Objects;

public class StoreItem {
    private String id;
    private String name;
    private Long price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StoreItem storeItem = (StoreItem) o;
        return Objects.equals(name, storeItem.name) && Objects.equals(price, storeItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
