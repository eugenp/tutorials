package com.baeldung.hibernate.pojo.inheritance;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Pen extends MyProduct {
    private String color;

    public Pen() {
    }

    public Pen(long productId, String name, String color) {
        super(productId, name);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
