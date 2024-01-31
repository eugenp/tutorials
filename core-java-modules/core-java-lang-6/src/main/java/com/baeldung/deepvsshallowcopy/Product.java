package com.baeldung.deepvsshallowcopy;

public class Product implements Cloneable {

    private String name;
    private double price;
    private Category category;

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Product deepCopy() {
        return new Product(name, price, new Category(category.getName(), category.getDescription()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Product clone() {
        Product clone;
        try {
            clone = (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            clone = new Product(this.getName(), this.getPrice(), this.getCategory());
        }
        clone.category = this.category.clone();
        return clone;
    }
}
