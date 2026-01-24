package com.baeldung.testng.executeselectively;

import org.testng.IMethodInterceptor;
import org.testng.IMethodInstance;
import org.testng.ITestContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SkipMethodInterceptor implements IMethodInterceptor {

    private static final Set<String> SKIP_METHODS = Set.of("givenTest_whenFails_thenExecuteSelectively");

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        return methods.stream()
                .filter(m -> !SKIP_METHODS.contains(m.getMethod().getMethodName()))
                .collect(Collectors.toList());
    }
}
