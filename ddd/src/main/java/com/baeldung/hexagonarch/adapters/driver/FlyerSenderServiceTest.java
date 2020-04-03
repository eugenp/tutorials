package com.baeldung.hexagonarch.adapters.driver;

import org.hexagonarch.ports.driver.MailBoxService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class FlyerSenderServiceTest {

    private MailBoxService mailBoxService;

    public FlyerSenderServiceTest(MailBoxService mailBoxService) {
        this.mailBoxService = mailBoxService;
    }

    @Test
    public void sendFlyersTest() {
        String streetName = "Abbey Road";
        String city = "London";
        String zipCode = "NW8";
        Assertions.assertDoesNotThrow(() -> mailBoxService.sendFlyer(streetName, city, zipCode));
    }
}