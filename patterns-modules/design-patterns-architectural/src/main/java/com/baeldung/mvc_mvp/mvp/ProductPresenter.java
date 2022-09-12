package com.baeldung.mvc_mvp.mvp;

public class ProductPresenter {
    private final Product product;
    private final ProductView view;
    
    public ProductPresenter(Product product, ProductView view) {
        this.product = product;
        this.view = view;
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
    
    public Double getProductPrice() {
        return product.getPrice();
    }
    
    public void setPrice(Double price) {
        product.setPrice(price);
    }
    
    public void showProduct() {
        view.printProductDetails(product.getName(), product.getDescription(), product.getPrice());
    }
    
}
