package com.baeldung.system.exit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.security.Permission;

import static org.junit.Assert.assertEquals;

@Ignore("This test is ignored because it tests deprecated code")
public class SystemExitUnitTest {

    private SecurityManager securityManager;
    private SystemExitExample example;

    @Before
    public void setUp() {
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
}