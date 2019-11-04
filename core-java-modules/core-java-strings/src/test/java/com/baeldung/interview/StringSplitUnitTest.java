package com.baeldung.interview;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class StringSplitUnitTest {
    @Test
    public void givenCoreJava_whenSplittingStrings_thenSplitted() {
        String expected[] = {
          "john",
          "peter",
          "mary"
        };
        
        String[] splitted = "john,peter,mary".split(",");
        assertArrayEquals( expected, splitted );
    }
    
    @Test
    public void givenApacheCommons_whenSplittingStrings_thenSplitted() {
        String expected[] = {
          "john",
          "peter",
          "mary"
        };
        String[] splitted = StringUtils.split("john peter mary");
        assertArrayEquals( expected, splitted );
    }
    
    
}
