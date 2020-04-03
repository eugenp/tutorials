package com.baeldung.hexagonarch.main;

import org.hexagonarch.adapters.driven.PersonDatabaseRepository;
import org.hexagonarch.adapters.driver.FlyerSenderServiceTest;
import org.hexagonarch.ports.driven.PersonRepository;
import org.hexagonarch.ports.driver.MailBoxService;

import java.lang.reflect.Constructor;

public class Main {
    public static void main(String[] args) {

        MailBoxService mailbox = null;
        try {
            mailbox = Main.createMailbox();
        } catch (Exception e) {
            System.err.println("Something went wrong !");
        }
        FlyerSenderServiceTest flyerSenderServiceTest = new FlyerSenderServiceTest(mailbox);
        flyerSenderServiceTest.sendFlyersTest();
    }

    private static MailBoxService createMailbox() throws Exception {
        Class<PersonRepository> constructorArgClass = PersonRepository.class;
        PersonDatabaseRepository constructorArg = new PersonDatabaseRepository();
        Constructor<?> mailBoxConstructor = Class
                .forName("org.hexagonarch.core.service.MailBoxServiceImpl")
                .getDeclaredConstructor(constructorArgClass);
        mailBoxConstructor.setAccessible(true);
        return (MailBoxService) mailBoxConstructor.newInstance(constructorArg);
    }
}
