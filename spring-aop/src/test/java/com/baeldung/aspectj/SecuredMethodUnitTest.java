package com.baeldung.aspectj;

import org.junit.Test;

public class SecuredMethodUnitTest {
    @Test
    public void testMethod() throws Exception {
        SecuredMethod service = new SecuredMethod();
        service.unlockedMethod();
        service.lockedMethod();
    }
}