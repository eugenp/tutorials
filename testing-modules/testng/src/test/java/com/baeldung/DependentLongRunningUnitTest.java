package com.baeldung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DependentLongRunningUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependentLongRunningUnitTest.class);

    private String email = "abc@qwe.com";

    @Test
    public void givenEmail_ifValid_thenTrue() {
        boolean valid = email.contains("@");
        Assert.assertEquals(valid, true);
    }

    @Test(dependsOnMethods = {"givenEmail_ifValid_thenTrue"})
    public void givenValidEmail_whenLoggedIn_thenTrue() {
        LOGGER.info("Email {} valid >> logging in", email);
    }
}

