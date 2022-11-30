package com.baeldung.exception.exceptions_vs_errors;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

// Unit test for the ErrorExample class.
public class ErrorExampleUnitTest {
    @Test
    public void whenMainMethodIsRun_thenStackOverflowError() {
        Assertions.assertThrows(StackOverflowError.class, 
            () -> ErrorExample.main(null));
    }
}
