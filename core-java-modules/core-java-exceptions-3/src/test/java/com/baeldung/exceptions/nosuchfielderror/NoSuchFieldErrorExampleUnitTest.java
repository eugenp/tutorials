package com.baeldung.exceptions.nosuchfielderror;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoSuchFieldErrorExampleUnitTest {

    @Test
    public void whenDependentMessage_returnMessage() {
        String dependentMessage = NoSuchFieldErrorExample.getDependentMessage();
        assertTrue("Hello Baeldung".equals(dependentMessage));
    }

}
