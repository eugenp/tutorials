package com.baeldung.boot.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {

    private String color;
    private String grade;

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_TYPE_ID")
    private ItemType itemType;

    private String name;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    public String getColor() {
        return color;
    }

    public String getGrade() {
        return grade;
    }

    public Long getId() {
        return id;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Store getStore() {
        return store;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
