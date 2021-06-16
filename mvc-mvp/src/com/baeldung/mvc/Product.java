package com.baeldung.mvc;

public class Product {
    private String productName;
    private String productDescription;
    private Double productPrice;
    private ProductView productView;
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductDescription() {
        return productDescription;
    }
    
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    
    public Double getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
    
    public ProductView getProductView() {
        return productView;
    }
    
    public void setProductView(ProductView productView) {
        this.productView = productView;
    }
    
    public void showProduct() {
        productView.printProductDetails(productName, productDescription, productPrice);
    }
    
}
