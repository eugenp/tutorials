package com.baeldung.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.function.Supplier;

@Component
public class ShoppingDaoImpl implements ShoppingDao {
    @Autowired
    private CartEntryRepository cartEntryRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProduct(Long cartId, Long productId, Long quantity) {
        final Supplier<EntityNotFoundException> notFound = EntityNotFoundException::new;
        final Cart cart = cartRepository.findById(cartId).orElseThrow(notFound);
        final Product product = productRepository.findById(cartId).orElseThrow(notFound);
        CartEntry cartEntry = cartEntryRepository.findByCartAndProduct(cart, product);
        if (cartEntry == null) {
            cartEntry = new CartEntry(cart, product, quantity);
        } else {
            cartEntry.setQuantity(cartEntry.getQuantity() + quantity);
        }
        cartEntryRepository.save(cartEntry);
    }
    // other CRUD operations
}