package com.baeldung.springflyway;

import com.baeldung.springflyway.entities.Customer;
import com.baeldung.springflyway.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryInitialMigrationTest {

    @Autowired CustomerRepository customerRepository;

    @Test
    public void givenASetOfMigrations_whenRunningMigrationsWithSuccess_thenACustomerIsFound() {
        Optional<Customer> customerOptional = customerRepository.findByEmail("email@email.com");
        assertTrue(customerOptional.isPresent());

    }

    @Test
    public void givenSchemaCreationMigration_whenTryingToCreateACustomer_thenSuccess() {
        Customer customer = customerRepository.save(Customer
          .builder()
          .email("customer@email.com")
          .build());
        assertNotNull(customer.getId());
    }

}
