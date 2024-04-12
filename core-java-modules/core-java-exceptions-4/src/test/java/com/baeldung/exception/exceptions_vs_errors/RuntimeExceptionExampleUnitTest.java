package com.baeldung.exception.exceptions_vs_errors;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

// Unit test for the RuntimeExceptionExample class.
public class RuntimeExceptionExampleUnitTest {
    @Test
    public void whenMainMethodIsRun_thenArrayIndexOutOfBoundsExceptionIsThrown() {
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, 
            () -> RuntimeExceptionExample.main(null));
    }
}
