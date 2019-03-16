package com.baeldung.nulls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PrimitivesAndWrapperTest {

    @Test
    public void givenWrappers_whenBothArgsNonNull_thenReturnResult() {

        Integer sum = PrimitivesAndWrapper.wrapperSum(0, 0);

        assertEquals(0, sum.intValue());
    }

    @Test()
    public void givenWrappers_whenOneArgIsNull_thenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> PrimitivesAndWrapper.wrapperSum(null, 2));
    }

    @Test()
    public void givenWrappers_whenBothArgsAreNull_thenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> PrimitivesAndWrapper.wrapperSum(null, null));
    }

    @Test()
    public void givenWrappersWithNullCheck_whenAnyArgIsNull_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PrimitivesAndWrapper.goodSum(null, 2));
    }



}