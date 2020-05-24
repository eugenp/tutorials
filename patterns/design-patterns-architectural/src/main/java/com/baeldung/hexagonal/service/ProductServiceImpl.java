package com.baeldung.hexagonal.service;

import java.util.List;

import com.baeldung.hexagonal.core.ProductManager;
import com.baeldung.hexagonal.domain.Product;

public class ProductServiceImpl implements ProductService {

    ProductManager prdManager = new ProductManager();

    public void createProduct(String name, String category, int stock, double price) {
        Product p = new Product();
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setStock(stock);
        prdManager.createProduct(p);

    }

    public void applyDiscount(int discount, int productId) {
        Product p = prdManager.getProduct(productId);
        p.setDiscount(discount);
        prdManager.updateProduct(p);
    }

    public List<Product> getAllProducts() {
        return prdManager.getAll();

    }

}
