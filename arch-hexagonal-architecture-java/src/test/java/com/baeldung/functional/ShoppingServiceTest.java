package com.baeldung.functional;

import com.baeldung.persistence.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingServiceTest {

    @InjectMocks
    private ShoppingDaoImpl shoppingDao;

    @Mock
    private CartEntryRepository cartEntryRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenCartAndProductExists_andCartEntryDoesNotExist_thenCartEntryCreated() {
        long cartId = 2L;
        long productId = 3L;

        Cart cart = new Cart();
        cart.setId(cartId);
        Optional<Cart> optionalCart = Optional.of(cart);

        Product product = new Product();
        product.setId(productId);
        Optional<Product> optionalProduct = Optional.of(product);

        when(cartRepository.findById(cartId)).thenReturn(optionalCart);
        when(productRepository.findById(productId)).thenReturn(optionalProduct);
        when(cartEntryRepository.findByCartAndProduct(cart, product)).thenReturn(null);

        shoppingDao.addProductToCart(cartId, productId, 10L);
    }


    @Test
    public void whenCartAndProductExists_andCartEntryExists_thenCartEntryUpdated() {
        long cartId = 2L;
        long productId = 3L;

        Cart cart = new Cart();
        cart.setId(cartId);
        Optional<Cart> optionalCart = Optional.of(cart);

        Product product = new Product();
        product.setId(productId);
        Optional<Product> optionalProduct = Optional.of(product);

        CartEntry cartEntry = new CartEntry();
        cartEntry.setCart(cart);
        cartEntry.setProduct(product);
        cartEntry.setQuantity(4);

        when(cartRepository.findById(cartId)).thenReturn(optionalCart);
        when(productRepository.findById(productId)).thenReturn(optionalProduct);
        when(cartEntryRepository.findByCartAndProduct(cart, product)).thenReturn(cartEntry);

        shoppingDao.addProductToCart(cartId, productId, 10L);
    }
}
