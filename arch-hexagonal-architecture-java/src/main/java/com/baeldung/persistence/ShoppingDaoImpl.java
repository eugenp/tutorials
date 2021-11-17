package com.baeldung.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.function.Supplier;

@Component
public class ShoppingDaoImpl implements ShoppingDao {
    private static final Logger LOG = LoggerFactory.getLogger(ShoppingDaoImpl.class);

    @Autowired
    private CartEntryRepository cartEntryRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProductToCart(Long cartId, Long productId, Long quantity) {
        LOG.info("About to add product {} with quantity {} to cart {}", productId, quantity, cartId);
        final Supplier<EntityNotFoundException> notFound = EntityNotFoundException::new;
        final Cart cart = cartRepository.findById(cartId).orElseThrow(notFound);
        final Product product = productRepository.findById(productId).orElseThrow(notFound);
        CartEntry cartEntry = cartEntryRepository.findByCartAndProduct(cart, product);
        if (cartEntry == null) {
            LOG.debug("Cart entry does not exist. Creating new entry.");
            cartEntry = new CartEntry(cart, product, quantity);
        } else {
            LOG.debug("Cart entry {} exists.", cartEntry.getId());
            cartEntry.setQuantity(cartEntry.getQuantity() + quantity);
        }
        cartEntryRepository.save(cartEntry);
        LOG.info("Updated product {} with quantity {} to cart {}", productId, quantity, cartId);
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public Product createProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode);
        if (product == null) {
            product = new Product();
            product.setProductCode(productCode);
            productRepository.save(product);
        }
        return product;
    }

    // other CRUD operations
}