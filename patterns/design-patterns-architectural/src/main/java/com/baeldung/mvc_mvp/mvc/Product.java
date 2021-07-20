package com.baeldung.mvc_mvp.mvc;

public class Product {
    private String name;
    private String description;
    private Double price;
    private ProductView view;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public ProductView getView() {
        return view;
    }
    
    public void setView(ProductView view) {
        this.view = view;
    }
    
    public void showProduct() {
        view.printProductDetails(name, description, price);
    }
    
}
