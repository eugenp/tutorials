package com.baeldung.spring.data.jpa.flush;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.flush.AppConfig;
import com.baeldung.flush.Customer;
import com.baeldung.flush.CustomerAddress;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class })
public class FlushIntegrationTest {

    private static final Customer EXPECTED_CUSTOMER = aCustomer();

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    void givenANewCustomer_whenPersistAndNoFlush_thenDatabaseNotSynchronizedWithPersistentContextUsingCommitFlushMode() {

        entityManager.setFlushMode(FlushModeType.COMMIT);

        EntityTransaction transaction = getTransaction();
        Customer customer = saveCustomerInPersistentContext("Alice", 30);
        Customer customerInContext = entityManager.find(Customer.class, customer.getId());
        assertDataInPersitentContext(customerInContext);

        TypedQuery<Customer> retrievedCustomer = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'Alice'", Customer.class);

        List<Customer> resultList = retrievedCustomer.getResultList();

        assertThat(resultList).isEmpty();
        transaction.rollback();
    }

    @Test
    void givenANewCustomer_whenPersistAndFlush_thenDatabaseSynchronizedWithPersistentContextUsingCommitFlushMode() {
        entityManager.setFlushMode(FlushModeType.COMMIT);

        EntityTransaction transaction = getTransaction();
        Customer customer = saveCustomerInPersistentContext("Alice", 30);
        entityManager.flush();
        Long generatedCustomerID = customer.getId();

        Customer customerInContext = entityManager.find(Customer.class, generatedCustomerID);
        assertDataInPersitentContext(customerInContext);

        TypedQuery<Customer> retrievedCustomer = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'Alice'", Customer.class);

        Customer result = retrievedCustomer.getSingleResult();
        assertThat(result).isEqualTo(EXPECTED_CUSTOMER);
        transaction.rollback();
    }

    @Test
    public void givenANewCustomer_whenPersistAndFlush_thenCustomerIdGeneratedToBeAddedInAddress() {
        entityManager.setFlushMode(FlushModeType.COMMIT);
        EntityTransaction transaction = getTransaction();

        saveCustomerInPersistentContext("John", 25);
        entityManager.flush();

        Customer retrievedCustomer = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'John'", Customer.class)
          .getSingleResult();
        Long customerId = retrievedCustomer.getId();

        CustomerAddress address = new CustomerAddress();
        address.setCustomer_id(customerId);
        entityManager.persist(address);
        entityManager.flush();

        CustomerAddress customerAddress = entityManager.createQuery("SELECT a FROM CustomerAddress a WHERE a.customer_id = :customerID", CustomerAddress.class)
          .setParameter("customerID", customerId)
          .getSingleResult();

        assertThat(customerAddress).isNotNull();
        transaction.rollback();
    }

    @Test
    void givenANewCustomer_whenPersistAndNoFlush_thenDBIsSynchronizedWithThePersistentContextWithAutoFlushMode() {
        entityManager.setFlushMode(FlushModeType.AUTO);
        EntityTransaction transaction = getTransaction();

        Customer customer = saveCustomerInPersistentContext("Alice", 30);
        Customer customerInContext = entityManager.find(Customer.class, customer.getId());
        assertDataInPersitentContext(customerInContext);

        TypedQuery<Customer> retrievedCustomer = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'Alice'", Customer.class);

        Customer result = retrievedCustomer.getSingleResult();

        assertThat(result).isEqualTo(EXPECTED_CUSTOMER);

        transaction.rollback();
    }

    @Test
    public void givenFlushModeAutoAndNewCustomer_whenPersistAndNoFlush_thenCustomerIdGeneratedToBeAddedInAddress() {
        entityManager.setFlushMode(FlushModeType.AUTO);
        EntityTransaction transaction = getTransaction();

        saveCustomerInPersistentContext("John", 25);

        Customer singleResult = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = 'John'", Customer.class)
          .getSingleResult();
        Long customerId = singleResult.getId();

        CustomerAddress address = new CustomerAddress();
        address.setCustomer_id(customerId);
        entityManager.persist(address);

        CustomerAddress customerAddress = entityManager.createQuery("SELECT a FROM CustomerAddress a WHERE a.customer_id = :customerID", CustomerAddress.class)
          .setParameter("customerID", customerId)
          .getSingleResult();

        assertThat(customerAddress).isNotNull();
        transaction.rollback();
    }

    @Test
    public void givenFlushModeAutoAndNewCustomer_whenPersistAndFlush_thenCustomerIdGeneratedToBeAddedInAddress() {
        entityManager.setFlushMode(FlushModeType.AUTO);
        EntityTransaction transaction = getTransaction();

        saveCustomerInPersistentContext("John", 25);

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

        assertThat(customerAddress).isNotNull();

        transaction.rollback();
    }

    private static void assertDataInPersitentContext(Customer customerInContext) {
        assertThat(customerInContext).isNotNull();
        assertThat(customerInContext.getName()).isEqualTo("Alice");
    }

    private Customer saveCustomerInPersistentContext(String name, int age) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAge(age);
        entityManager.persist(customer);
        return customer;
    }

    @AfterEach
    public void cleanup() {
        entityManager.clear();
    }

    private static Customer aCustomer() {
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setAge(30);
        return customer;
    }

    private EntityTransaction getTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        return transaction;
    }
}