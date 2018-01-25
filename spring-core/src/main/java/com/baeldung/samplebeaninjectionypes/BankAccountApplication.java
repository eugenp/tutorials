package com.baeldung.samplebeaninjectionypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BankAccountApplication {


    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("samplebeaninjectiontypes-context.xml");
        BankAccountWithSetterInjection bankAccountWithSetterInjection = (BankAccountWithSetterInjection) context.getBean("bankAccountWithSetterInjectionBean");

        bankAccountWithSetterInjection.openAccount(12345L,"Savings","John Doe");

        BankAccountWithConstructorInjection bankAccountWithConstructorInjection = (BankAccountWithConstructorInjection) context.getBean("bankAccountWithConstructorInjectionBean");
        bankAccountWithSetterInjection.openAccount(12345L,"Savings","John Doe");

    }
}
