package com.baeldung.service;

import com.baeldung.entity.User;
import com.baeldung.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MultiDbServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Transactional("productTransactionManager")
    @Test
    void givenUsersAndProducts_whenSaved_thenFoundById() {
        User user = new User();
        user.setId(1L);
        user.setName("Alice");

        Product product = new Product();
        product.setId(100L);
        product.setName("Laptop");

        productService.save(product);
        userService.save(user);

        Optional<User> retrievedUser = userService.findById(1L);
        Optional<Product> retrievedProduct = productService.findById(100L);

        assertTrue(retrievedUser.isPresent());
        assertEquals("Alice", retrievedUser.get().getName());

        assertTrue(retrievedProduct.isPresent());
        assertEquals("Laptop", retrievedProduct.get().getName());
    }
}
