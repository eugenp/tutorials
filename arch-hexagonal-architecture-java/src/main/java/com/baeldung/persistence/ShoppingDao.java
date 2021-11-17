package com.baeldung.persistence;

public interface ShoppingDao {
    void addProductToCart(Long cartId, Long productId, Long quantity);
    Cart createCart(User user);
    Product createProduct(String productCode);
    // other CRUD operations
}