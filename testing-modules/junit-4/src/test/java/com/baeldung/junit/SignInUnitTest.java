package com.baeldung.junit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignInUnitTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInUnitTest.class);

    @Test
    public void whenCalledFromSuite_thanOK() {
        LOGGER.info("SignIn successful");
    }
}
