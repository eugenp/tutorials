package com.baeldung.hexagonal.adapters;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.baeldung.hexagonal.service.ProductService;

public class ProductServiceWebAdapter {

    private ProductService productService;

    public ProductServiceWebAdapter(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/products/create/{name}/{category}/{stock}/{price}")
    public void createProduct(
        @PathVariable("name") String name, 
        @PathVariable("category") String category, 
        @PathVariable("stock") int stock, 
        @PathVariable("price") double price) {
        productService.createProduct(name, category, stock, price);
    }

    @PostMapping(path = "/products/update/{discount}/{productID}")
    public void applyDiscount(
        @PathVariable("discount") int discount, 
        @PathVariable("productId") int productId) {
        productService.applyDiscount(discount, productId);
    }

}
