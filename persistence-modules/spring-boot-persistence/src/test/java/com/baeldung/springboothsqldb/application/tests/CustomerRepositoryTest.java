package com.baeldung.hsqldb.application.tests;

import com.baeldung.springboothsqldb.application.entities.Customer;
import com.baeldung.springboothsqldb.application.repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Test
    public void whenFindingCustomerById_thenCorrect() {
        customerRepository.save(new Customer("John", "john@domain.com"));
        
        assertThat(customerRepository.findById(1L)).isInstanceOf(Optional.class);
    }
    
    @Test
    public void whenFindingAllCustomers_thenCorrect() {
        customerRepository.save(new Customer("John", "john@domain.com"));
        
        assertThat(customerRepository.findAll()).isInstanceOf(List.class);
    }
    
    @Test
    public void whenSavingCustomer_thenCorrect() {
        customerRepository.save(new Customer("Bob", "bob@domain.com"));
        Customer customer = customerRepository.findById(1L).orElseGet(() -> new Customer("john", "john@domain.com"));
        
        assertThat(customer.getName()).isEqualTo("Bob");
    }
}
