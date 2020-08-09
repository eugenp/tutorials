package com.baeldung.repository;

import com.baeldung.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CustomerRepositoryIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CustomerRepository repository;

    @Before
    public void before() {
        entityManager.persist(new Customer("A", "A@example.com"));
        entityManager.persist(new Customer("D", null));
        entityManager.persist(new Customer("D", "D@example.com"));
    }

    @Test
    public void givenQueryMethod_whenEmailIsNull_thenFoundByNullEmail() {
        List<Customer> customers = repository.findByNameAndEmail("D", null);

        assertEquals(1, customers.size());
        Customer actual = customers.get(0);

        assertEquals(null, actual.getEmail());
        assertEquals("D", actual.getName());
    }

    @Test
    public void givenQueryMethod_whenEmailIsAbsent_thenIgnoreEmail() {
        List<Customer> customers = repository.findByName("D");

        assertEquals(2, customers.size());
    }

    @Test
    public void givenQueryAnnotation_whenEmailIsNull_thenIgnoreEmail() {
        List<Customer> customers = repository.findCustomerByNameAndEmail("D", null);

        assertEquals(2, customers.size());
    }

    @After
    public void cleanUp() {
        repository.deleteAll();
    }
}