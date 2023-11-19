package com.baeldung.executeonce;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AtomicBooleanInitializerUnitTest {

    @Test
    void givenAtomicBooleanInitializer_whenRepeatedlyCallingInitialize_thenCallCountIsOne() {
        AtomicBooleanInitializer atomicBooleanInitializer = new AtomicBooleanInitializer();
        assertEquals(0, atomicBooleanInitializer.callCount);

        atomicBooleanInitializer.initialize();
        atomicBooleanInitializer.initialize();
        atomicBooleanInitializer.initialize();

        assertEquals(1, atomicBooleanInitializer.callCount);
    }
}