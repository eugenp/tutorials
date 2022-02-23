package com.baeldung.readonlytransactions;

import com.baeldung.readonlytransactions.h2.Config;
import com.baeldung.readonlytransactions.h2.Transaction;
import com.baeldung.readonlytransactions.h2.TransactionConfig;
import com.baeldung.readonlytransactions.h2.TransactionService;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, initializers = SpringTransactionReadOnlyIntegrationTest.TestConfig.class, classes = { TransactionService.class})
class SpringTransactionReadOnlyIntegrationTest {

    static class TestConfig implements ApplicationContextInitializer<GenericApplicationContext> {
        @Override public void initialize(GenericApplicationContext applicationContext) {
            final AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(applicationContext);

            beanDefinitionReader.register(Config.class);
            beanDefinitionReader.register(TransactionConfig.class);
        }
    }

    @Autowired
    @Qualifier("h2EntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private TransactionService service;

    @BeforeEach
    void setUp() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Transaction").executeUpdate();

        final Transaction transaction = new Transaction();
        transaction.setName("Test 1");
        transaction.setUuid(UUID.randomUUID().toString());

        entityManager.merge(transaction);
        entityManager.getTransaction().commit();
    }

    @Test
    void givenThatSpringTransactionManagementIsEnabled_whenAMethodIsAnnotatedAsTransactionalReadOnly_thenSpringShouldTakeCareOfTheTransaction() {
        Transaction transaction = service.getTransactionById(1L);

        assertNotNull(transaction);
    }
}
