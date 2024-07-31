package com.baeldung.methodoverloadingoverriding;

import com.baeldung.methodoverloadingoverriding.util.Multiplier;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class MethodOverloadingUnitTest {
    
    private static Multiplier multiplier;
    
    @BeforeClass
    public static void setUpMultiplierInstance() {
        multiplier = new Multiplier();
    }
 
    @Test
    public void givenMultiplierInstance_whenCalledMultiplyWithTwoIntegers_thenOneAssertion() {
        assertThat(multiplier.multiply(10, 10)).isEqualTo(100);
    }
    
    @Test
    public void givenMultiplierInstance_whenCalledMultiplyWithTreeIntegers_thenOneAssertion() {
        assertThat(multiplier.multiply(10, 10, 10)).isEqualTo(1000);
    }
    
    @Test
    public void givenMultiplierInstance_whenCalledMultiplyWithIntDouble_thenOneAssertion() {
        assertThat(multiplier.multiply(10, 10.5)).isEqualTo(105.0);
    }
    
    @Test
    public void givenMultiplierInstance_whenCalledMultiplyWithDoubleDouble_thenOneAssertion() {
        assertThat(multiplier.multiply(10.5, 10.5)).isEqualTo(110.25);
    }
    
    @Test
    public void givenMultiplierInstance_whenCalledMultiplyWithIntIntAndMatching_thenNoTypePromotion() {
        assertThat(multiplier.multiply(10, 10)).isEqualTo(100);
    }
}
