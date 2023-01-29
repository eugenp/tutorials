package com.baeldung.exception.exceptions_vs_errors;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ErrorExampleUnitTest {

    @Test
    public void whenMainMethodIsRun_thenStackOverflowError() {
        Assertions.assertThrows(AssertionError.class,
          () -> ErrorExample.main(null));
    }
}
