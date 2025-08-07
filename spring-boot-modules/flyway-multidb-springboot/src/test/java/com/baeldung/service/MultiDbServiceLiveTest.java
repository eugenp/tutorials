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
class MultiDbServiceLiveTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Transactional("productTransactionManager")
    @Test
    void givenUsersAndProducts_whenSaved_thenFoundById() {
        User user = new User();
        user.setName("Alice");

        Product product = new Product();
        product.setName("Laptop");

        // Save and capture returned entities to get generated IDs
        Product savedProduct = productService.save(product);
        User savedUser = userService.save(user);


        // Use the generated IDs for retrieval
        Optional<User> retrievedUser = userService.findById(savedUser.getId());
        Optional<Product> retrievedProduct = productService.findById(savedProduct.getId());

        // Assertions
        assertTrue(retrievedUser.isPresent());
        assertEquals("Alice", retrievedUser.get().getName());

        assertTrue(retrievedProduct.isPresent());
        assertEquals("Laptop", retrievedProduct.get().getName());
    }
}
