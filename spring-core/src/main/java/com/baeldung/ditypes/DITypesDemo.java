package com.baeldung.ditypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DITypesDemo {
    
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("ditypes-context.xml");
        
        CheckingAccount checkingAccount = (CheckingAccount)context.getBean("checkingAccount");
        checkingAccount.processTransaction();
    }
}
