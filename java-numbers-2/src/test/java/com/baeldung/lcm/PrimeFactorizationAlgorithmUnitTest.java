package com.baeldung.lcm;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.baeldung.lcm.PrimeFactorizationAlgorithm.*;


public class PrimeFactorizationAlgorithmUnitTest {

    @Test
    public void testGetPrimeFactors() {
        Map<Integer, Integer> expectedPrimeFactorsMapForTwelve = new HashMap<>();
        expectedPrimeFactorsMapForTwelve.put(2, 2);
        expectedPrimeFactorsMapForTwelve.put(3, 1);
        Map<Integer, Integer> expectedPrimeFactorsMapForEighteen = new HashMap<>();
        expectedPrimeFactorsMapForEighteen.put(2, 1);
        expectedPrimeFactorsMapForEighteen.put(3, 2);
        Assert.assertEquals(expectedPrimeFactorsMapForTwelve, getPrimeFactors(12));
        Assert.assertEquals(expectedPrimeFactorsMapForEighteen, getPrimeFactors(18));
    }

    @Test
    public void testLCM() {
        Assert.assertEquals(36, PrimeFactorizationAlgorithm.lcm(12, 18));
    }
}
