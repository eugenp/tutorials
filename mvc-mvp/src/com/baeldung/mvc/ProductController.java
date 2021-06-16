package com.baeldung.mvc;

public class ProductController {
    private final Product product;
    
    public ProductController(Product product) {
        this.product = product;
    }
    
    public String getProductName() {
        return product.getProductName();
    }
    
    public void setProductName(String productName) {
        product.setProductName(productName);
    }
    
    public String getProductDescription() {
        return product.getProductDescription();
    }
    
    public void setProductDescription(String productDescription) {
        product.setProductDescription(productDescription);
    }
    
    public Double getProductPrice() {
        return product.getProductPrice();
    }
    
    public void setProductPrice(Double productPrice) {
        product.setProductPrice(productPrice);
    }
    
}
