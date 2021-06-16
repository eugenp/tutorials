package com.baeldung.mvp;

public class ProductPresenter {
    private final Product product;
    private final ProductView productView;
    
    public ProductPresenter(Product product, ProductView productView) {
        this.product = product;
        this.productView = productView;
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
    
    public void showProduct() {
        productView.printProductDetails(product.getProductName(), product.getProductDescription(), product.getProductPrice());
    }
    
}
