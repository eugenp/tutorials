package com.baeldung.algorithms.editdistance;

import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

public class EditDistanceDataProvider {

    @Parameters
    public static Collection<Object[]> getLists() {
        return Arrays.asList(new Object[][] { 
            { "", "", 0 }, 
            { "ago", "", 3 }, 
            { "", "do", 2 }, 
            { "abc", "adc", 1 }, 
            { "peek", "pesek", 1 }, 
            { "sunday", "saturday", 3 } 
        });
    }
}
