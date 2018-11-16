package com.baeldung.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item {

    private String color;
    private String grade;

    @Id
    private Long id;

    @ManyToOne
    private ItemType itemType;

    private String name;
    private BigDecimal price;
    @ManyToOne
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
