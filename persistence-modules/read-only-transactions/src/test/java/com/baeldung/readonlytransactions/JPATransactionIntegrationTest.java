package com.baeldung.readonlytransactions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.readonlytransactions.h2.Config;
import com.baeldung.readonlytransactions.h2.Book;
import com.baeldung.readonlytransactions.mysql.spring.ReadOnlyInterception;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, initializers = JPATransactionIntegrationTest.TestConfig.class, classes = { ReadOnlyInterception.class })
class JPATransactionIntegrationTest {

    static class TestConfig implements ApplicationContextInitializer<GenericApplicationContext> {
        @Override
        public void initialize(GenericApplicationContext applicationContext) {
            new AnnotatedBeanDefinitionReader(applicationContext).register(Config.class);
        }
    }

    @Autowired
    @Qualifier("h2EntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction()
            .begin();
        entityManager.createQuery("DELETE FROM Book")
            .executeUpdate();

        Book book = new Book();
        book.setName("Test 1");
        book.setUuid(UUID.randomUUID()
            .toString());

        entityManager.merge(book);
        entityManager.getTransaction()
            .commit();
    }

    @Test
    void givenAEntityManagerDefinedAsReadOnly_whenCreatingATransaction_thenAReadOnlyTransactionShouldBeCreated() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.unwrap(Session.class)
            .setDefaultReadOnly(true);
        entityManager.getTransaction()
            .begin();
        Book book = entityManager.find(Book.class, 1L);
        entityManager.getTransaction()
            .commit();
        entityManager.unwrap(Session.class)
            .setDefaultReadOnly(false);

        assertNotNull(book);
    }

}
