package com.baeldung.persistence;

public interface ShoppingDao {
    void addProduct(Long cartId, Long productId, Long quantity);
    // other CRUD operations
}