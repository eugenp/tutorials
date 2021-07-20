package com.baeldung.mvc_mvp.mvc;

public class ProductController {
    private final Product product;
    
    public ProductController(Product product) {
        this.product = product;
    }
    
    public String getName() {
        return product.getName();
    }
    
    public void setName(String name) {
        product.setName(name);
    }
    
    public String getDescription() {
        return product.getDescription();
    }
    
    public void setDescription(String description) {
        product.setDescription(description);
    }
    
    public Double getPrice() {
        return product.getPrice();
    }
    
    public void setPrice(Double price) {
        product.setPrice(price);
    }
    
}
