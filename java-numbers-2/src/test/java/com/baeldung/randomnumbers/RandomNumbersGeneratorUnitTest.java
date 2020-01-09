package com.baeldung.randomnumbers;

import org.junit.Test;

public class RandomNumbersGeneratorUnitTest {
    
    @Test(expected = Test.None.class)
    public void whenRandomNumbers_worksOK() {
        new RandomNumbersGenerator().randomNumbers();
    }

}
