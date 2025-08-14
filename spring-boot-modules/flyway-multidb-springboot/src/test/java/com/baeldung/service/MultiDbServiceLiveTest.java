package com.baeldung.service;

import com.baeldung.entity.User;
import com.baeldung.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.baeldung.repository.user.UserRepository;
import com.baeldung.repository.product.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MultiDbServiceLiveTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional("productTransactionManager")
    @Test
    void givenUsersAndProducts_whenSaved_thenFoundById() {
        User user = new User();
        user.setName("John");
        userRepository.save(user);

        Product product = new Product();
        product.setName("Laptop");
        productRepository.save(product);

        assertTrue(userRepository.findById(user.getId()).isPresent());
        assertTrue(productRepository.findById(product.getId()).isPresent());
    }
}
