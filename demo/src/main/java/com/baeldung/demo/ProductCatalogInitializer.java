package com.baeldung.demo;

public class ProductCatalogInitializer {

    public ProductCatalogInitializer() {
        loadProducts();
    }

    private void loadProducts() {
        System.out.println("Starting product preload");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Product preload completed");
    }

    public String getStatus() {
        return "Catalog ready";
    }
}
    

