package com.baeldung.testng;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class FactoryNameTest {
    private final String instanceLabel;

    public FactoryNameTest(String label) {
        this.instanceLabel = label;
    }

    @Factory
    public static Object[] build() {
        return new Object[]{new FactoryNameTest("fast-path"), new FactoryNameTest("slow-path")};
    }

    @BeforeMethod
    public void capture(Method method, ITestResult result) {
        String fullName = method.getName() + "[" + instanceLabel + "]";
        result.setAttribute("displayName", fullName);
        System.out.println("→ " + fullName);
    }

    @Test
    public void executeScenario() { /* … */ }
}
