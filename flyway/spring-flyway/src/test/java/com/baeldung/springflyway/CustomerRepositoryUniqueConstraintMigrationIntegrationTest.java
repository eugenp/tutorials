package com.baeldung.springflyway;

import com.baeldung.springflyway.entities.Customer;
import com.baeldung.springflyway.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryUniqueConstraintMigrationIntegrationTest {

    @Autowired CustomerRepository customerRepository;

    @Test(expected = DataIntegrityViolationException.class)
    public void givenTheUniqueConstraintMigrations_whenInsertingAnExistingEmailCustomer_thenThrowException() {
        customerRepository.save(Customer
          .builder()
          .email("email@email.com")
          .build());

    }

}
