package com.baeldung.algorithms.interpolationsearch;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InterpolationSearchUnitTest {
    
    private int[] myData;

    @BeforeEach
    public void setUp() {
        myData = new int[]{13,21,34,55,69,73,84,101};
    }
    
    @Test
    void givenSortedArray_whenLookingFor84_thenReturn6() {
        int pos = InterpolationSearch.interpolationSearch(myData, 84);
        assertEquals(6, pos);
    }
    
    @Test
    void givenSortedArray_whenLookingFor19_thenReturnMinusOne() {
        int pos = InterpolationSearch.interpolationSearch(myData, 19);
        assertEquals(-1, pos);
    }

}
