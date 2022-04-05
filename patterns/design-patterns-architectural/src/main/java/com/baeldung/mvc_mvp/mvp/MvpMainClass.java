package com.baeldung.mvc_mvp.mvp;

public class MvpMainClass {
    
    public static void main(String[] args) {
        
        Product model = retrieveProductFromDatabase();
        ProductView view = new ProductView();
        ProductPresenter presenter = new ProductPresenter(model, view);
        presenter.showProduct();
        presenter.setName("SmartPhone");
        presenter.showProduct();
    }
    
    private static Product retrieveProductFromDatabase() {
        Product product = new Product();
        product.setName("Mobile");
        product.setDescription("New Brand");
        product.setPrice(1000.0);
        return product;
    }
}
