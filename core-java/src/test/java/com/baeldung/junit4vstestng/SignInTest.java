package com.baeldung.junit4vstestng;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignInTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInTest.class);

    @Test
    public void whenCalledFromSuite_thanOK() {
        LOGGER.info("SignIn successful");
    }
}
