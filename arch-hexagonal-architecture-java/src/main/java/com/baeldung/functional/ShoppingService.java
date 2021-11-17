package com.baeldung.functional;

import com.baeldung.persistence.Cart;
import com.baeldung.persistence.User;

public interface ShoppingService {

    void addProduct(Long cartNo, Long productNo, Long quantity);

    void removeProduct(Long cartNo, Long productNo, Long quantity);

    void checkout(Long cartNo);

}
