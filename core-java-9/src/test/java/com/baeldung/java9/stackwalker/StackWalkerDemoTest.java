package com.baeldung.java9.stackwalker;

import org.junit.Test;

public class StackWalkerDemoTest {

    @Test
    public void walkTheStack() {
        new StackWalkerDemo().methodOne();
    }
}
