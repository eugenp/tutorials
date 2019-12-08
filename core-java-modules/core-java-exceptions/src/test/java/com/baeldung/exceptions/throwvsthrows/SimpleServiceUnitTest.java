package com.baeldung.exceptions.throwvsthrows;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleServiceUnitTest {

    SimpleService simpleService = new SimpleService();

    @Test
    void whenSQLExceptionIsThrown_thenShouldBeRethrownWithWrappedException() {
        assertThrows(DataAccessException.class,
                () -> simpleService.wrappingException());
    }

    @Test
    void whenCalled_thenNullPointerExceptionIsThrown() {
        assertThrows(NullPointerException.class,
                () -> simpleService.runtimeNullPointerException());
    }
}