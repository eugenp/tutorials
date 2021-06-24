package com.baeldung.mvc_mvp.mvp;

public class MvpMainClass {
    
    public static void main(String[] args) {
        
        Product model = retrieveProductFromDatabase();
        ProductView view = new ProductView();
        ProductPresenter presenter = new ProductPresenter(model, view);
        presenter.showProduct();
        presenter.setProductName("SmartPhone");
        presenter.showProduct();
    }
    
    private static Product retrieveProductFromDatabase() {
        Product product = new Product();
        product.setProductName("Mobile");
        product.setProductDescription("New Brand");
        product.setProductPrice(1000.0);
        return product;
    }
}
