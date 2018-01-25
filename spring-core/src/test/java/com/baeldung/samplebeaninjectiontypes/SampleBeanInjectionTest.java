package com.baeldung.samplebeaninjectiontypes;

import com.baeldung.dependencyinjectiontypes.ArticleWithSetterInjection;
import com.baeldung.samplebeaninjectionypes.AccountDetails;
import com.baeldung.samplebeaninjectionypes.BankAccountWithConstructorInjection;
import com.baeldung.samplebeaninjectionypes.BankAccountWithSetterInjection;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertTrue;

public class SampleBeanInjectionTest {

    @Test
    public void testSetterInjectionValid() {

        ApplicationContext context = new ClassPathXmlApplicationContext("samplebeaninjectiontypes-context.xml");
        BankAccountWithSetterInjection bankAccountWithSetterInjection = (BankAccountWithSetterInjection) context.getBean("bankAccountWithSetterInjection");

        String owner = "John Doe";
        AccountDetails accountDetails = bankAccountWithSetterInjection.openAccount(12345L,"Savings",owner);

        assertTrue(accountDetails.getAccountName().equals(owner));

    }

    @Test
    public void testConstructorInjectionValid() {

        ApplicationContext context = new ClassPathXmlApplicationContext("samplebeaninjectiontypes-context.xml");
        BankAccountWithConstructorInjection bankAccountWithConstructorInjection = (BankAccountWithConstructorInjection) context.getBean("bankAccountWithConstructorInjectionBean");

        String owner = "John Doe";
        AccountDetails accountDetails = bankAccountWithConstructorInjection.openAccount(12345L,"Savings",owner);

        assertTrue(accountDetails.getAccountName().equals(owner));
    }
}
