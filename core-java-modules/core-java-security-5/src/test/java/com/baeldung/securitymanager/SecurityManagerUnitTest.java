package com.baeldung.securitymanager;

import java.net.URI;
import java.security.AccessControlException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SecurityManagerUnitTest {

    private static final String TESTING_SECURITY_POLICY = "file:src/test/resources/testing.policy";

    @Before
    public void setUp() {
        System.setProperty("java.security.policy", TESTING_SECURITY_POLICY);
        System.setSecurityManager(new SecurityManager());
    }

    @After
    public void tearDown() {
        System.setSecurityManager(null);
    }

    @Test(expected = AccessControlException.class)
    public void whenSecurityManagerIsActive_thenNetworkIsNotAccessibleByDefault() throws Exception {
        new URI("http://www.google.com").toURL().openConnection().connect();
    }

    @Test(expected = AccessControlException.class)
    public void whenUnauthorizedClassTriesToAccessProtectedOperation_thenAnExceptionIsThrown() {
        new Service().operation();
    }
}
