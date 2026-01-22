package com.baeldung.testng.executeselectively;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Set;

@Listeners(SkipMethodInterceptor.class)
public class ExecuteSelectivelyUnitTest {
    private static final Set<String> SKIP_METHODS = Set.of("givenTest_whenFails_thenExecuteSelectively");

    @BeforeMethod
    public void skipSelectedMethods(Method method) {
        if (SKIP_METHODS.contains(method.getName())) {
            throw new SkipException("Skipping test method: " + method.getName());
        }
    }

    @Test
    public void givenTest_whenFails_thenExecuteSelectively() {
        Assert.assertEquals(5, 6);
    }

    @Test
    public void givenTest_whenPass_thenExecuteSelectively() {
        Assert.assertEquals(5, 5);
    }
}
