package org.baeldung.spring43.defaultmethods;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(classes = TransactionalTestConfiguration.class)
public class TransactionalIntegration extends AbstractTransactionalJUnit4SpringContextTests implements ITransactional {

    @Test
    public void whenDefaultMethodAnnotatedWithBeforeTransaction_thenDefaultMethodIsExecuted() {
    }

}
