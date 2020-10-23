package com.baeldung.exceptions.nosuchmethoderror;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

public class NoSuchFieldErrorTest {

    @Test(expected = NoSuchFieldException.class)
    public void whenFieldNotFound_thenThrowNoSuchFieldException() {
        NoSuchFieldError2.print();
    }
}
