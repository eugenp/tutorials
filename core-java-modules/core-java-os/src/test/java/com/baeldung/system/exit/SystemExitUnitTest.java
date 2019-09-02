package com.baeldung.system.exit;

import static org.junit.Assert.assertEquals;

import java.security.Permission;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemExitUnitTest {

    protected static class ExitException extends SecurityException {

        private static final long serialVersionUID = 1L;
        public final int status;

        public ExitException(int status) {
            this.status = status;
        }
    }

    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
        }

        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }

    private SecurityManager securityManager;

    private SystemExitExample example;

    @Before
    public void setUp() throws Exception {
        example = new SystemExitExample();
        securityManager = System.getSecurityManager();
        System.setSecurityManager(new NoExitSecurityManager());
    }

    @After
    public void tearDown() throws Exception {
        System.setSecurityManager(securityManager);
    }

    @Test
    public void testExit() throws Exception {
        try {
            example.readFile();
        } catch (ExitException e) {
            assertEquals("Exit status", 2, e.status);
        }
    }
}