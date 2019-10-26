package com.baeldung.tx;

import com.baeldung.model.Payment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@Transactional(propagation = NOT_SUPPORTED)
class ManualTransactionIntegrationTest {

    @Container
    private static PostgreSQLContainer<?> pg = initPostgres();

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntityManager entityManager;

    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @AfterEach
    void flushDb() {
        transactionTemplate.execute(status -> entityManager
                .createQuery("delete from Payment")
                .executeUpdate());
    }

    @Test
    void givenAPayment_WhenNotDuplicate_ThenShouldCommit() {
        Long id = transactionTemplate.execute(status -> {
            Payment payment = new Payment();
            payment.setAmount(1000L);
            payment.setReferenceNumber("Ref-1");
            payment.setState(Payment.State.SUCCESSFUL);

            entityManager.persist(payment);

            return payment.getId();
        });

        Payment payment = entityManager.find(Payment.class, id);
        assertThat(payment).isNotNull();
    }

    @Test
    void givenAPayment_WhenMarkAsRollback_ThenShouldRollback() {
        transactionTemplate.execute(status -> {
            Payment payment = new Payment();
            payment.setAmount(1000L);
            payment.setReferenceNumber("Ref-1");
            payment.setState(Payment.State.SUCCESSFUL);

            entityManager.persist(payment);
            status.setRollbackOnly();

            return payment.getId();
        });

        assertThat(entityManager
                .createQuery("select p from Payment p")
                .getResultList()).isEmpty();
    }

    @Test
    void givenTwoPayments_WhenRefIsDuplicate_ThenShouldRollback() {
        try {
            transactionTemplate.execute(s -> {
                Payment first = new Payment();
                first.setAmount(1000L);
                first.setReferenceNumber("Ref-1");
                first.setState(Payment.State.SUCCESSFUL);

                Payment second = new Payment();
                second.setAmount(2000L);
                second.setReferenceNumber("Ref-1");
                second.setState(Payment.State.SUCCESSFUL);

                entityManager.persist(first);
                entityManager.persist(second);

                return "Ref-1";
            });
        } catch (Exception ignored) {
        }

        assertThat(entityManager
                .createQuery("select p from Payment p")
                .getResultList()).isEmpty();
    }

    @Test
    void givenAPayment_WhenNotExpectingAnyResult_ThenShouldCommit() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Payment payment = new Payment();
                payment.setReferenceNumber("Ref-1");
                payment.setState(Payment.State.SUCCESSFUL);

                entityManager.persist(payment);
            }
        });

        assertThat(entityManager
                .createQuery("select p from Payment p")
                .getResultList()).hasSize(1);
    }

    @Test
    void givenAPayment_WhenUsingTxManager_ThenShouldCommit() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setTimeout(3);

        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            Payment payment = new Payment();
            payment.setReferenceNumber("Ref-1");
            payment.setState(Payment.State.SUCCESSFUL);

            entityManager.persist(payment);
            transactionManager.commit(status);
        } catch (Exception ex) {
            transactionManager.rollback(status);
        }

        assertThat(entityManager
                .createQuery("select p from Payment p")
                .getResultList()).hasSize(1);
    }

    private static PostgreSQLContainer<?> initPostgres() {
        PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:11.1")
                .withDatabaseName("baeldung")
                .withUsername("test")
                .withPassword("test");
        pg.setPortBindings(singletonList("54320:5432"));

        return pg;
    }
}
