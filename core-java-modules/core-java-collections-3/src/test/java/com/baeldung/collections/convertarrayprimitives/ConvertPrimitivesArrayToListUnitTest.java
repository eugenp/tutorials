package com.baeldung.collections.iterators;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import com.google.common.primitives.Ints;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
