package com.baeldung.sneakythrows;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SneakyRunnableTest {

    @Test(expected = InterruptedException.class)
    public void whenCallSneakyRunnableMethod_thenThrowException() {
        new SneakyRunnable().run();
    }
}
