package com.baeldung.springflyway;

import com.baeldung.springflyway.entities.Customer;
import com.baeldung.springflyway.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryInsertDataMigrationTest {

    @Autowired CustomerRepository customerRepository;

    @Test
    public void givenASetInsertData_whenRunningMigrationsWithSuccess_thenASpecificCustomerIsFound() {
        Optional<Customer> customerOptional = customerRepository.findByEmail("email@email.com");
        assertTrue(customerOptional.isPresent());
    }

    @Test
    public void givenASetInsertData_whenRunningMigrationsWithSuccess_thenASetOfCustomersIsFound() {
        List<Customer> customers = customerRepository.findAll();
        assertNotNull(customers);
        assertEquals(customers.size(), 6);
    }

}
