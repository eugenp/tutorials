package com.baeldung.evenodd;

import static com.baeldung.evenodd.EvenOdd.isAndEven;
import static com.baeldung.evenodd.EvenOdd.isAndOdd;
import static com.baeldung.evenodd.EvenOdd.isEven;
import static com.baeldung.evenodd.EvenOdd.isLsbEven;
import static com.baeldung.evenodd.EvenOdd.isLsbOdd;
import static com.baeldung.evenodd.EvenOdd.isOdd;
import static com.baeldung.evenodd.EvenOdd.isOrEven;
import static com.baeldung.evenodd.EvenOdd.isOrOdd;
import static com.baeldung.evenodd.EvenOdd.isXorEven;
import static com.baeldung.evenodd.EvenOdd.isXorOdd;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EvenOddUnitTest {

    @Test
    public void whenNumberIsEven_thenReturnTrue() {
        assertEquals(true, isEven(2));
    }

    @Test
    public void whenNumberIsOdd_thenReturnTrue() {
        assertEquals(true, isOdd(3));
    }

    @Test
    public void whenNumberIsEven_thenReturnTrueWithOr() {
        assertEquals(true, isOrEven(4));
    }

    @Test
    public void whenNumberIsOdd_thenReturnTrueOr() {
        assertEquals(true, isOrOdd(5));
    }

    @Test
    public void whenNumberIsEven_thenReturnTrueAnd() {
        assertEquals(true, isAndEven(6));
    }

    @Test
    public void whenNumberIsOdd_thenReturnTrueAnd() {
        assertEquals(true, isAndOdd(7));
    }

    @Test
    public void whenNumberIsEven_thenReturnTrueXor() {
        assertEquals(true, isXorEven(8));
    }

    @Test
    public void whenNumberIsOdd_thenReturnTrueXor() {
        assertEquals(true, isXorOdd(9));
    }

    @Test
    public void whenNumberIsEven_thenReturnTrueLsb() {
        assertEquals(true, isLsbEven(10));
    }

    @Test
    public void whenNumberIsOdd_thenReturnTrueLsb() {
        assertEquals(true, isLsbOdd(11));
    }
}
