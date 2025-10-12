package com.baeldung.mainclasstest;

import org.junit.jupiter.api.Test;

public class ApplicationArgumentsTest {
    @Test
    public void testMainWithArguments() {
        String[] args = { "--spring.profiles.active=test" };
        Application.main(args);
    }
}
