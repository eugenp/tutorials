package com.baeldung.testng;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class NameByReflectionTest {

    @BeforeMethod
    public void capture(Method method) {
        String testName = method.getName();
        System.out.printf("→ About to run %s%n", testName);
    }

    @Test
    public void shouldPersistEntity() { /* … */ }
}
