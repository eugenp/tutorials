package org.baeldung.persistence.service;

import org.baeldung.config.ProductConfig;
import org.baeldung.config.UserConfig;
import org.baeldung.persistence.multiple.dao.product.ProductRepository;
import org.baeldung.persistence.multiple.dao.user.PossessionRepository;
import org.baeldung.persistence.multiple.dao.user.UserRepository;
import org.baeldung.persistence.multiple.model.product.Product;
import org.baeldung.persistence.multiple.model.user.Possession;
import org.baeldung.persistence.multiple.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserConfig.class, ProductConfig.class })
@EnableTransactionManagement
@DirtiesContext
public class JpaMultipleDBIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PossessionRepository possessionRepository;

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
        Possession p = new Possession("sample");
        p = possessionRepository.save(p);
        user.setPossessionList(Collections.singletonList(p));
        user = userRepository.save(user);
        final Optional<User> result = userRepository.findById(user.getId());
        assertTrue(result.isPresent());
        System.out.println(result.get().getPossessionList());
        assertEquals(1, result.get().getPossessionList().size());
    }

    @Test
    @Transactional("userTransactionManager")
    public void whenCreatingUsersWithSameEmail_thenRollback() {
        User user1 = new User();
        user1.setName("John");
        user1.setEmail("john@test.com");
        user1.setAge(20);
        user1 = userRepository.save(user1);
        assertTrue(userRepository.findById(user1.getId()).isPresent());

        User user2 = new User();
        user2.setName("Tom");
        user2.setEmail("john@test.com");
        user2.setAge(10);
        try {
            user2 = userRepository.save(user2);
            userRepository.flush();
            fail("DataIntegrityViolationException should be thrown!");
        } catch (final DataIntegrityViolationException e) {
            // Expected
        } catch (final Exception e) {
            fail("DataIntegrityViolationException should be thrown, instead got: " + e);
        }
    }

    @Test
    @Transactional("productTransactionManager")
    public void whenCreatingProduct_thenCreated() {
        Product product = new Product();
        product.setName("Book");
        product.setId(2);
        product.setPrice(20);
        product = productRepository.save(product);

        assertTrue(productRepository.findById(product.getId()).isPresent());
    }

}
