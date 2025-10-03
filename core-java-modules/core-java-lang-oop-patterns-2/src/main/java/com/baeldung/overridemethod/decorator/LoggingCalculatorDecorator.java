package com.baeldung.overridemethod.decorator;

import com.baeldung.overridemethod.Calculator;
import java.util.HashMap;
import java.util.Map;

public class MeteredCalculator implements Calculator {
    private final Calculator wrappedCalculator;
    private final Map<String, Integer> methodCalls;

    public MeteredCalculator(Calculator calculator) {
        this.wrappedCalculator = calculator;
        this.methodCalls = new HashMap<>();
        // Initialize counts for clarity
        methodCalls.put("add", 0);
        methodCalls.put("subtract", 0);
    }

    @Override
    public int add(int a, int b) {
        // Track the call count
        methodCalls.merge("add", 1, Integer::sum);
        return wrappedCalculator.add(a, b); // Delegation
    }

    @Override
    public int subtract(int a, int b) {
        // Track the call count
        methodCalls.merge("subtract", 1, Integer::sum);
        return wrappedCalculator.subtract(a, b); // Delegation
    }

    // Public method to expose the call counts for testing
    public int getCallCount(String methodName) {
        return methodCalls.getOrDefault(methodName, 0);
    }
}
