package com.baeldung.tx;

import com.baeldung.boot.Application;
import com.baeldung.boot.domain.Payment;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("tc")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@ContextConfiguration(initializers = { ManualTransactionIntegrationTest.Initializer.class })
public class ManualTransactionIntegrationTest {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntityManager entityManager;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    private TransactionTemplate transactionTemplate;

    @Before
    public void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @After
    public void flushDb() {
        transactionTemplate.execute(status -> entityManager
                .createQuery("delete from Payment")
                .executeUpdate());
    }

    @Test
    public void givenAPayment_WhenNotDuplicate_ThenShouldCommit() {
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
    public void givenAPayment_WhenMarkAsRollback_ThenShouldRollback() {
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
    public void givenTwoPayments_WhenRefIsDuplicate_ThenShouldRollback() {
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
    public void givenAPayment_WhenNotExpectingAnyResult_ThenShouldCommit() {
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
    public void givenAPayment_WhenUsingTxManager_ThenShouldCommit() {
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

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
