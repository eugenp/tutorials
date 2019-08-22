package com.baeldung.hexagonal.core;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findCartOfCustomer(Long customerId);

    void save(Cart cart);

}
