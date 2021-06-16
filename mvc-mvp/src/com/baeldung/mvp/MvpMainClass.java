package com.baeldung.mvp;

public class MvpMainClass {
    
    public static void main(String[] args) {
        
        Product model = retrieveProductFromDatabase();
        ProductView view = new ProductView();
        ProductController controller = new ProductController(model, view);
        controller.showProduct();
        controller.setProductName("SmartPhone");
        controller.showProduct();
    }
    
    private static Product retrieveProductFromDatabase() {
        Product product = new Product();
        product.setProductName("Mobile");
        product.setProductDescription("New Brand");
        product.setProductPrice(1000.0);
        return product;
    }
}
