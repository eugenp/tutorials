package com.baeldung.collections.convertarrayprimitives;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class ConvertPrimitivesArrayToListUnitTest {

    @Test
    public void givenArrayWithPrimitives_whenIterativeConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), ConvertPrimitivesArrayToList.iterateConvert(new int[]{1,2,3,4}));
    }

    @Test
    public void givenArrayWithPrimitives_whenStreamConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), ConvertPrimitivesArrayToList.streamConvert(new int[]{1,2,3,4}));
    }

    @Test
    public void givenArrayWithPrimitives_whenIntStreamConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), ConvertPrimitivesArrayToList.streamConvertIntStream(new int[]{1,2,3,4}));
    }

    @Test
    public void givenArrayWithPrimitives_whenGuavaConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), ConvertPrimitivesArrayToList.guavaConvert(new int[]{1,2,3,4}));
    }

    @Test
    public void givenArrayWithPrimitives_whenApacheCommonConvert_thenArrayGetsConverted() {
        assertEquals(Arrays.asList(1,2,3,4), ConvertPrimitivesArrayToList.apacheCommonConvert(new int[]{1,2,3,4}));
    }
}
