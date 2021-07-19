package com.baeldung.java9.currentmethod;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurrentExecutingMethodUnitTest {

    @Test
    public void givenJava9_whenWalkingTheStack_thenFindMethod() {
        StackWalker walker = StackWalker.getInstance();
        Optional<String> methodName = walker.walk(frames -> frames
          .findFirst()
          .map(StackWalker.StackFrame::getMethodName)
        );

        assertTrue(methodName.isPresent());
        assertEquals("givenJava9_whenWalkingTheStack_thenFindMethod", methodName.get());
    }
}
