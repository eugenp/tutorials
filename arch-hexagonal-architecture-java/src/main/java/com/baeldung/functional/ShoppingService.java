package com.baeldung.functional;

public interface ShoppingService {

    void addProduct(Long cartNo, Long productNo, Long quantity);

    void removeProduct(Long cartNo, Long productNo, Long quantity);

    void checkout(Long cartNo);

}
