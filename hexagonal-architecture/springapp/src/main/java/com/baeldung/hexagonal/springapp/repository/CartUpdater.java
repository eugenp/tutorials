package com.baeldung.hexagonal.springapp.repository;

import com.baeldung.hexagonal.springapp.entity.CartEntity;

public interface CartUpdater {

    void update(CartEntity cart);

}
