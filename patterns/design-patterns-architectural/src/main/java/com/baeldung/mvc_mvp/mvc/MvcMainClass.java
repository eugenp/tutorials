package com.baeldung.mvc_mvp.mvc;

public class MvcMainClass {
    
    public static void main(String[] args) {
        
        Product model = retrieveProductFromDatabase();
        ProductView view = new ProductView();
        model.setView(view);
        model.showProduct();
        
        ProductController controller = new ProductController(model);
        controller.setName("SmartPhone");
        model.showProduct();
    }
    
    private static Product retrieveProductFromDatabase() {
        Product product = new Product();
        product.setName("Mobile");
        product.setDescription("New Brand");
        product.setPrice(1000.0);
        return product;
    }
}
