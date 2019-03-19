package com.baeldung.nulls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PrimitivesAndWrapperUnitTest {

    @Test
    public void givenBothArgsNonNull_whenCallingWrapperSum_thenReturnSum() {

        Integer sum = PrimitivesAndWrapper.wrapperSum(0, 0);

        assertEquals(0, sum.intValue());
    }

    @Test()
    public void givenOneArgIsNull_whenCallingWrapperSum_thenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> PrimitivesAndWrapper.wrapperSum(null, 2));
    }

    @Test()
    public void givenBothArgsNull_whenCallingWrapperSum_thenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> PrimitivesAndWrapper.wrapperSum(null, null));
    }

    @Test()
    public void givenOneArgNull_whenCallingGoodSum_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PrimitivesAndWrapper.goodSum(null, 2));
    }



}