package com.baeldung.dependency;

import org.springframework.stereotype.Component;

@Component
public class TestDependency {
    // This is the logic that this dependency will do.
    public int addNumbers(int a, int b) {
        return (a + b);
    }
}