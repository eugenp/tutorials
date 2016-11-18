package com.baeldung.aspectj.test;

import org.junit.Test;

import com.baeldung.aspectj.SecuredMethod;

public class SecuredMethodTest {
    @Test
    public void testMethod() throws Exception {
        SecuredMethod service = new SecuredMethod();
        service.unlockedMethod();
        service.lockedMethod();
    }
}