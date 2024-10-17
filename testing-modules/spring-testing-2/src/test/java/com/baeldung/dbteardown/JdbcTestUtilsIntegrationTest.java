package com.baeldung.dbteardown;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;

@SpringBootTest
@ActiveProfiles("hsql")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
public class JdbcTestUtilsIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterAll
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "customers");
    }

    @Test
    void givenCustomer_whenSaved_thenReturnSameCustomer() throws Exception {
        Customer customer = new Customer("John", "john@domain.com");

        Customer savedCustomer = customerRepository.save(customer);

        Assertions.assertEquals(customer, savedCustomer);
    }

    @Test
    void givenExistingCustomer_whenFoundByName_thenReturnCustomer() throws Exception {
        Optional<Customer> customer = customerRepository.findByName("John");

        Assertions.assertTrue(customer.isPresent(), "Customer should be present");
        Assertions.assertEquals("John", customer.get()
            .getName(), "Customer name should match");
    }

    @Test
    void givenNonExistingCustomer_whenFoundByName_thenReturnNull() throws Exception {
        Optional<Customer> customer = customerRepository.findByName("Doe");

        Assertions.assertFalse(customer.isPresent(), "Customer should not be found");
    }
}
