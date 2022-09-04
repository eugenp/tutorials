package com.baeldung.aspectj;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

public class SecuredMethodUnitTest {
    @Test
    public void testMethod() throws Exception {
        SecuredMethod service = new SecuredMethod();
        assertNotNull(service);
        service.unlockedMethod();
        service.lockedMethod();
    }
}