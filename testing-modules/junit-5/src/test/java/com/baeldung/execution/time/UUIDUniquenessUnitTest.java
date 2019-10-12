package com.baeldung.execution.time;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UUIDUniquenessUnitTest {

    @Test
    public void whenTwoUUIDThenAssertNotEqual() {

        assertNotEquals(UUID.randomUUID(), UUID.randomUUID());
    }
}
