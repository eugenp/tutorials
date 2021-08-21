package com.baeldung.hexagonal.domain;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.adapter.out.Product;
import com.baeldung.hexagonal.domain.ports.ProductRepository;

@Service
public class SimpleShoppingCartService implements ShoppingCartService {

    private final ShoppingCart shoppingCart;

    @Autowired
    ProductRepository productRepository;

    public SimpleShoppingCartService() {
        this.shoppingCart = new ShoppingCart(new HashMap<>());
    }

    public Float getTotalCartValue() {
        return shoppingCart.getTotalCartValue();
    }

    public void addItem(String name, Integer quantity) {
        Product product = productRepository.findByName(name);
        shoppingCart.addItem(product.toItem(), quantity);
    }
}
