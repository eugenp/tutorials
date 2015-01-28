package org.baeldung.persistence.service;

import static org.junit.Assert.assertNotNull;

import org.baeldung.config.MultipleDBJPAConfig;
import org.baeldung.config.ProductConfig;
import org.baeldung.config.UserConfig;
import org.baeldung.persistence.multiple.dao.product.ProductRepository;
import org.baeldung.persistence.multiple.dao.user.UserRepository;
import org.baeldung.persistence.multiple.model.product.Product;
import org.baeldung.persistence.multiple.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MultipleDBJPAConfig.class, UserConfig.class, ProductConfig.class })
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
public class JPAMultipleDBTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenCreateUser_thenCreated() {
        User user = new User();
        user.setName("John");
        user.setAge(20);
        user = userRepository.save(user);

        assertNotNull(userRepository.findOne(user.getId()));
    }

    @Test
    public void whenCreateProduct_thenCreated() {
        Product product = new Product();
        product.setName("Book");
        product.setId(2);
        product.setPrice(20);
        product = productRepository.save(product);

        assertNotNull(productRepository.findOne(product.getId()));
    }
}
