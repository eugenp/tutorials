package com.baeldung.noclassdeffounderror;

import org.junit.Test;

public class NoClassDefFoundErrorTest {

    @Test(expected = NoClassDefFoundError.class)
    public void givenInitErrorInClass_whenloadClass_thenNoClassDefFoundError() {
        NoClassDefFoundErrorExample sample = new NoClassDefFoundErrorExample();
        sample.getClassWithInitErrors();
    }
}