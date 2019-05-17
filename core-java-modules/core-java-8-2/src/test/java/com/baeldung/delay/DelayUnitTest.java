package com.baeldung.delay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DelayUnitTest {

    @Test
    void givenMain_returnZero() {
        assertEquals(Delay.main(true), 0);
    }
    
}