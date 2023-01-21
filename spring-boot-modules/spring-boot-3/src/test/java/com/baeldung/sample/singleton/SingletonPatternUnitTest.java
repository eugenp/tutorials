package com.baeldung.sample.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class SingletonPatternUnitTest {

    @Test
    void givenTwoSingletonInstances_whenGettingThem_thenSameInstancesAreReturned() {
        SingletonPattern instanceOne = SingletonPattern.getInstance();
        SingletonPattern instanceTwo = SingletonPattern.getInstance();
        assertSame(instanceOne, instanceTwo);
    }

}
