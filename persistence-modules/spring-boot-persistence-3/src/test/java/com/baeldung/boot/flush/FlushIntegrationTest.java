package com.baeldung.boot.flush;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.flush.AppConfig;
import com.baeldung.flush.Customer;
import com.baeldung.flush.CustomerAddress;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class })

public class FlushIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void givenANewCustomer_whenPersistAndNoFlush_thenDatabaseNotSynchronizedWithPersistentContextUsingCommitFlushMode() {

        entityManager.setFlushMode(FlushModeType.COMMIT);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAge(30);
        entityManager.persist(customer);
        Customer customerInContext = entityManager.find(Customer.class, customer.getId());
        assertThat(customerInContext).isNotNull();
        assertThat(customerInContext.getName()).isEqualTo("Alice");

        TypedQuery<Customer> retrievedCustomer = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'Alice'", Customer.class);

        List<Customer> resultList = retrievedCustomer.getResultList();

        assertThat(resultList).isEmpty();
        transaction.rollback();
    }

    @Test
    void givenANewCustomer_whenPersistAndFlush_thenDatabaseSynchronizedWithPersistentContextUsingCommitFlushMode() {
        entityManager.setFlushMode(FlushModeType.COMMIT);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAge(30);
        entityManager.persist(customer);
        entityManager.flush();
        Long generatedCustomerID = customer.getId();
        Customer customerInContext = entityManager.find(Customer.class, generatedCustomerID);
        assertThat(customerInContext).isNotNull();
        assertThat(customerInContext.getName()).isEqualTo("Alice");

        TypedQuery<Customer> retrievedCustomer = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'Alice'", Customer.class);

        List<Customer> resultList = retrievedCustomer.getResultList();

        assertThat(resultList).isNotEmpty();
        assertThat(resultList.get(0)
          .getName()).isEqualTo("Alice");
        assertThat(resultList.get(0)
          .getAge()).isEqualTo(30);
        assertThat(resultList.get(0)
          .getId()).isEqualTo(generatedCustomerID);
        transaction.rollback();
    }

    @Test
    void givenANewCustomer_whenPersistAndNoFlush_thenDBIsSyncronizedWithThePersistentContextWithAutoFlushMode() {
        entityManager.setFlushMode(FlushModeType.AUTO);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAge(30);
        entityManager.persist(customer);
        Customer customerInContext = entityManager.find(Customer.class, customer.getId());
        assertThat(customerInContext).isNotNull();
        assertThat(customerInContext.getName()).isEqualTo("Alice");

        TypedQuery<Customer> retrievedCustomer = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'Alice'", Customer.class);

        List<Customer> resultList = retrievedCustomer.getResultList();

        assertThat(resultList).isNotEmpty();
        assertThat(resultList.get(0)
          .getAge()).isEqualTo(30);
        assertThat(resultList.get(0)
          .getName()).isEqualTo("Alice");
        assertThat(resultList.get(0)
          .getId()).isEqualTo(customer.getId());
        transaction.rollback();
    }

    @Test
    public void givenANewCustomer_whenPersistAndFlush_thenCustomerIdGeneratedToBeAddedInAddress() {
        entityManager.setFlushMode(FlushModeType.COMMIT);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setName("John");
        customer.setAge(25);
        entityManager.persist(customer);
        entityManager.flush();

        Customer singleResult = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'John'", Customer.class)
          .getSingleResult();
        Long customerId = singleResult.getId();

        CustomerAddress address = new CustomerAddress();
        address.setCustomer_id(customerId);
        entityManager.persist(address);
        entityManager.flush();

        CustomerAddress customerAddress = entityManager.createQuery("SELECT a FROM CustomerAddress a WHERE a.customer_id = :customerID", CustomerAddress.class)
          .setParameter("customerID", customerId)
          .getSingleResult();

        Assertions.assertThat(customerAddress)
          .isNotNull();

        transaction.rollback();
    }

    @Test
    public void givenFlushModeAutoAndNewCustomer_whenPersistAndNoFlush_thenCustomerIdGeneratedToBeAddedInAddress() {
        entityManager.setFlushMode(FlushModeType.AUTO);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setName("John");
        customer.setAge(25);
        entityManager.persist(customer);

        Customer singleResult = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'John'", Customer.class)
          .getSingleResult();
        Long customerId = singleResult.getId();

        CustomerAddress address = new CustomerAddress();
        address.setCustomer_id(customerId);
        entityManager.persist(address);

        CustomerAddress customerAddress = entityManager.createQuery("SELECT a FROM CustomerAddress a WHERE a.customer_id = :customerID", CustomerAddress.class)
          .setParameter("customerID", customerId)
          .getSingleResult();

        Assertions.assertThat(customerAddress)
          .isNotNull();

        transaction.rollback();
    }

    @Test
    public void givenFlushModeCommitAndNewCustomer_whenPersistAndNoFlush_thenCustomerIdGeneratedToBeAddedInAddress() {
        entityManager.setFlushMode(FlushModeType.AUTO);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setName("John");
        customer.setAge(25);
        entityManager.persist(customer);

        Customer singleResult = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'John'", Customer.class)
          .getSingleResult();
        Long customerId = singleResult.getId();

        CustomerAddress address = new CustomerAddress();
        address.setCustomer_id(customerId);
        entityManager.persist(address);

        CustomerAddress customerAddress = entityManager.createQuery("SELECT a FROM CustomerAddress a WHERE a.customer_id = :customerID", CustomerAddress.class)
          .setParameter("customerID", customerId)
          .getSingleResult();

        Assertions.assertThat(customerAddress)
          .isNotNull();

        transaction.rollback();
    }

    @AfterEach
    public void cleanup() {
        entityManager.clear();
    }
}
