package com.baeldung.exceptions.sneakythrows;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SneakyRunnableUnitTest {

    @Test
    public void whenCallSneakyRunnableMethod_thenThrowException() {
        try {
            new SneakyRunnable().run();
        } catch (Exception e) {
            assertEquals(InterruptedException.class, e.getStackTrace());
        }
    }
}
