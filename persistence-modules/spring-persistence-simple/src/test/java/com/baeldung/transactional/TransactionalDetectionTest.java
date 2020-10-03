package com.baeldung.transactional;

import com.baeldung.persistence.service.transactional.PersistenceTransactionalTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = PersistenceTransactionalTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionalDetectionTest {

    @Test
    @Transactional
    public void givenTransactional_whenCheckingForActiveTransaction_thenReceiveTrue() {
        assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
    }

    @Test
    public void givenNoTransactional_whenCheckingForActiveTransaction_thenReceiveFalse() {
        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
    }
}
