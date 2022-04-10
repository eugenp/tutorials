package com.baeldung.projection.model;

import java.util.List;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "inventory")
public class Inventory {

    @MongoId
    private String id;
    private String item;
    private String status;
    private Size size;
    private List<InStock> inStock;

    public String getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public List<InStock> getInStock() {
        return inStock;
    }

    public void setInStock(List<InStock> inStock) {
        this.inStock = inStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(id, inventory.id) && Objects.equals(item, inventory.item) && Objects.equals(status, inventory.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, item, status);
    }

}
