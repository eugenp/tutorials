package com.baeldung.hexagonal.service;

import java.util.List;

import com.baeldung.hexagonal.domain.Product;

public interface ProductService {

    public void createProduct(String name, String category, int stock, double price);

    public void applyDiscount(int discount, int productId);

    public List<Product> getAllProducts();

}
