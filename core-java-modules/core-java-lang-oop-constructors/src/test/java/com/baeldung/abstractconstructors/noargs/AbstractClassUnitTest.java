package com.baeldung.abstractconstructors.noargs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AbstractClassUnitTest {

    @Test
    void givenNoArgsAbstractConstructor_whenNewSubclassA_thenCalled() {
        assertNotNull(new ConcreteClassA());
    }

    @Test
    void givenNoArgsAbstractConstructor_whenNewSubclassB_thenCalled() {
        assertNotNull(new ConcreteClassB());
    }
}
