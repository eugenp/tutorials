package com.baeldung.injection.withinjection.main;

import java.math.BigDecimal;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.injection.withinjection.configuration.AccountConfiguration;
import com.baeldung.injection.withinjection.consumer.AccountTransferImpl;

public class AccountTransferMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AccountConfiguration.class);
        AccountTransferImpl app = context.getBean(AccountTransferImpl.class);
        app.deposit(new BigDecimal("50.00"));
        context.close();
    }

}
