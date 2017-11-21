package com.baeldung.springflyway;

import com.baeldung.springflyway.entities.Customer;
import com.baeldung.springflyway.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryIntegrationTest {

    @Autowired CustomerRepository customerRepository;

    @Before
    public void setUp() {
        customerRepository.save(Customer
          .builder()
          .firstName("firstName")
          .lastName("lastName")
          .email("email@test.com")
          .build());
    }

    @Test
    public void givenAnEmailAddress_whenFindByEmail_thenACustomerIsFound() {
        Optional<Customer> customerOptional = customerRepository.findByEmail("email@test.com");
        assertTrue(customerOptional.isPresent());

    }

}
