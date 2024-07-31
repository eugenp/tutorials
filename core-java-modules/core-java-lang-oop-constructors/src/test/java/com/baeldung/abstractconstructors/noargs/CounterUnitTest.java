package com.baeldung.abstractconstructors.noargs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CounterUnitTest {

    @Test
    void givenNoArgAbstractConstructor_whenSubclassCreation_thenCalled() {
        Counter counter = new SimpleCounter(1);
        assertNotNull(counter);
        assertEquals(1, counter.value);
    }
}
