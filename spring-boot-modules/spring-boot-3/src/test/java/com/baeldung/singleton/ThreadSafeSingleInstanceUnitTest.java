package com.baeldung.singleton;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class ThreadSafeSingleInstanceUnitTest {

    @Test
    void givenTwoSingletonInstances_whenGettingThem_thenSameInstancesAreReturned() {
        ThreadSafeSingleInstance instanceOne = ThreadSafeSingleInstance.getInstance();
        ThreadSafeSingleInstance instanceTwo = ThreadSafeSingleInstance.getInstance();
        assertSame(instanceOne, instanceTwo);
    }

}
