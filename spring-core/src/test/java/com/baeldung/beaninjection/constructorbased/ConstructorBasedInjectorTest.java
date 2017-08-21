package com.baeldung.beaninjection.constructorbased;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.beaninjection.model.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ConstructorBasedAppConfig.class)
public class ConstructorBasedInjectorTest {

    @Autowired
    private Transaction transaction;

    @Test
    public void givenConstructorInjectedTransaction_whenPrintSummary_shouldSucceed() throws Exception {
        transaction.printSummary();
    }
}
