package com.baeldung.java9.stackwalker;

import org.junit.Test;

public class StackWalkerDemoTest {

    @Test
    public void shouldWalkTheStack() {
        new StackWalkerDemo().methodOne();
    }
    
    @Test
    public void shouldFindCaller() {
        new StackWalkerDemo().findCaller();
    }
}
