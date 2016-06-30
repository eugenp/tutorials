package org.baeldung.persistence.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.baeldung.config.ProductConfig;
import org.baeldung.config.UserConfig;
import org.baeldung.persistence.multiple.dao.product.ProductRepository;
import org.baeldung.persistence.multiple.dao.user.UserRepository;
import org.baeldung.persistence.multiple.model.product.Product;
import org.baeldung.persistence.multiple.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserConfig.class, ProductConfig.class })
public class JpaMultipleDBIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // tests

    @Test
    @Transactional("userTransactionManager")
    public void whenCreatingUser_thenCreated() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@test.com");
        user.setAge(20);
        user = userRepository.save(user);

        assertNotNull(userRepository.findOne(user.getId()));
    }

    @Test
    @Transactional("userTransactionManager")
    public void whenCreatingUsersWithSameEmail_thenRollback() {
        User user1 = new User();
        user1.setName("John");
        user1.setEmail("john@test.com");
        user1.setAge(20);
        user1 = userRepository.save(user1);
        assertNotNull(userRepository.findOne(user1.getId()));

        User user2 = new User();
        user2.setName("Tom");
        user2.setEmail("john@test.com");
        user2.setAge(10);
        try {
            user2 = userRepository.save(user2);
        } catch (final DataIntegrityViolationException e) {
        }

        assertNull(userRepository.findOne(user2.getId()));
    }

    @Test
    @Transactional("productTransactionManager")
    public void whenCreatingProduct_thenCreated() {
        Product product = new Product();
        product.setName("Book");
        product.setId(2);
        product.setPrice(20);
        product = productRepository.save(product);

        assertNotNull(productRepository.findOne(product.getId()));
    }

}
