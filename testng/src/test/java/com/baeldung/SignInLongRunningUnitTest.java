package com.baeldung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class SignInLongRunningUnitTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInLongRunningUnitTest.class);

    @Test
    public void whenCalledFromSuite_thanOK() {
        LOGGER.info("SignIn successful");
    }
}
