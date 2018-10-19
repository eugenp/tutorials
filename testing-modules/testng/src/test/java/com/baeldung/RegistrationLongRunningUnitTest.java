package com.baeldung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class RegistrationLongRunningUnitTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationLongRunningUnitTest.class);

    @Test
    public void whenCalledFromSuite_thanOK() {
        LOGGER.info("Registration successful");
    }
}
