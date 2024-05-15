package com.baeldung.trifunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VavrFunction3ExampleUnitTest {

    @Test
    void whenMultiplyThenAdd_ThenReturnsCorrectResult() {
        assertEquals(25, VavrFunction3Example.multiplyThenAdd.apply(2, 10, 5));
    }

    @Test
    void whenMultiplyThenAddThenDivideByTen_ThenReturnsCorrectResult() {
        assertEquals(2, VavrFunction3Example.multiplyThenAddThenDivideByTen.apply(2, 10, 5));
    }

}
