package com.baeldung.sample.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class ThreadSafeSingleInstanceUnitTest {

    @Test
    void givenTwoSingletonInstances_whenGettingThem_thenSameInstancesAreReturned() {
        ThreadSafeSingleInstance instanceOne = ThreadSafeSingleInstance.getInstance();
        ThreadSafeSingleInstance instanceTwo = ThreadSafeSingleInstance.getInstance();
        assertSame(instanceOne, instanceTwo);
    }

}
