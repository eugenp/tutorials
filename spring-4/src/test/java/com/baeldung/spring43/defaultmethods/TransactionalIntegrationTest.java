package com.baeldung.spring43.defaultmethods;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(classes = TransactionalTestConfiguration.class)
public class TransactionalIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests implements ITransactionalUnitTest {

    @Test
    public void whenDefaultMethodAnnotatedWithBeforeTransaction_thenDefaultMethodIsExecuted() {
    }

}
