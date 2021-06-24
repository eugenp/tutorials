package com.baeldung.mvc_mvp.mvc;

public class MvcMainClass {
    
    public static void main(String[] args) {
        
        Product model = retrieveProductFromDatabase();
        ProductView view = new ProductView();
        model.setProductView(view);
        model.showProduct();
        
        ProductController controller = new ProductController(model);
        controller.setProductName("SmartPhone");
        model.showProduct();
    }
    
    private static Product retrieveProductFromDatabase() {
        Product product = new Product();
        product.setProductName("Mobile");
        product.setProductDescription("New Brand");
        product.setProductPrice(1000.0);
        return product;
    }
}
