package com.baeldung.ditypes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DITypesCheckingAccountTest {
    
    ApplicationContext context;

    @Before
    public void setUp() {
        this.context = new ClassPathXmlApplicationContext("ditypes-context.xml");
    }
    
    @Test
    public void givenXMLContext_whenBeanCreated_thenDependencyValid() {
        CheckingAccount checkingAccount = (CheckingAccount)context.getBean("checkingAccount");
        assertEquals("creditTransaction", checkingAccount.processCreditTransaction());
        assertEquals("debitTransaction", checkingAccount.processDebitTransaction());
    }
}
