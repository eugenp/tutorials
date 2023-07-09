package com.baeldung.deepshallowcopy.models;

public class Product implements Cloneable {
    private String name;
    private Category category;

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    // Shallow copy
    public Product shallowCopy() {
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    // Deep copy
    public Product deepCopy() {
        try {
            Product cloned = (Product) super.clone();
            cloned.category = this.category.clone(); // clone the Category object as well
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}