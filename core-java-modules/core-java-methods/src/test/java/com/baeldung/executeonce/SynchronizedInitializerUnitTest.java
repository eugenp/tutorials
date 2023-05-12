package com.baeldung.executeonce;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class SynchronizedInitializerUnitTest {

    @Test
    void givenSynchronizedInitializer_whenRepeatedlyCallingInitialize_thenCallCountIsOne() {
        SynchronizedInitializer synchronizedInitializer = new SynchronizedInitializer();
        assertEquals(0, synchronizedInitializer.callCount);

        synchronizedInitializer.initialize();
        synchronizedInitializer.initialize();
        synchronizedInitializer.initialize();

        assertEquals(1, synchronizedInitializer.callCount);
    }
}