package com.baeldung.java9.stackwalker;

import org.junit.Test;

public class StackWalkerDemoUnitTest {

    @Test
    public void giveStalkWalker_whenWalkingTheStack_thenShowStackFrames() {
        new StackWalkerDemo().methodOne();
    }
    
    @Test
    public void giveStalkWalker_whenInvokingFindCaller_thenFindCallingClass() {
        new StackWalkerDemo().findCaller();
    }
}
