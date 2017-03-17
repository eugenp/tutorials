package com.baeldung.injection.main;

import java.math.BigDecimal;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.injection.configuration.AccountConfiguration;
import com.baeldung.injection.consumer.AccountTransferImpl;

public class AccountTransferMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AccountConfiguration.class);
        AccountTransferImpl app = context.getBean(AccountTransferImpl.class);
        app.deposit(new BigDecimal("50.00"));
        context.close();
    }

}
