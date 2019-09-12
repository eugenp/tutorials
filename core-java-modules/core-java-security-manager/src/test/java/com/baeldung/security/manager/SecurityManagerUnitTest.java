package com.baeldung.security.manager;

import org.junit.Test;

import java.net.URL;
import java.security.AccessControlException;
import java.util.concurrent.Callable;

public class SecurityManagerUnitTest {

    @Test(expected = AccessControlException.class)
    public void whenSecurityManagerIsActive_thenNetworkIsNotAccessibleByDefault() throws Exception {
        doTest(() -> {
            new URL("http://www.google.com").openConnection().connect();
            return null;
        });
    }

    @Test(expected = AccessControlException.class)
    public void whenUnauthorizedClassTriesToAccessProtectedOperation_thenAnExceptionIsThrown() throws Exception {
        doTest(() -> {
            new Service().operation();
            return null;
        });
    }

    private void doTest(Callable<Void> action) throws Exception {
        System.setSecurityManager(new SecurityManager());
        try {
            action.call();
        } finally {
            System.setSecurityManager(null);
        }
    }
}
