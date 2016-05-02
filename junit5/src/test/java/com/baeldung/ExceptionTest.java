package com.baeldung;

import org.junit.gen5.api.Test;

import java.util.ArrayList;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.expectThrows;

public class ExceptionTest {

    @Test
    void shouldThrowException() {
        ArrayList list = null;
        Throwable exception = expectThrows(NullPointerException.class, list::clear);
        assertEquals(exception.getClass(), NullPointerException.class);
    }
}
