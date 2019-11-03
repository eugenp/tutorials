package com.baeldung.commons.lang3.test;

import org.apache.commons.lang3.math.Fraction;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class FractionUnitTest {
    
    @Test
    public void givenFractionClass_whenCalledgetFraction_thenCorrect() {
        assertThat(Fraction.getFraction(5, 6)).isInstanceOf(Fraction.class);
    }
    
    @Test
    public void givenTwoFractionInstances_whenCalledadd_thenCorrect() {
        Fraction fraction1 = Fraction.getFraction(1, 4);
        Fraction fraction2 = Fraction.getFraction(3, 4);
        assertThat(fraction1.add(fraction2).toString()).isEqualTo("1/1");
    }
    
    @Test
    public void givenTwoFractionInstances_whenCalledsubstract_thenCorrect() {
        Fraction fraction1 = Fraction.getFraction(3, 4);
        Fraction fraction2 = Fraction.getFraction(1, 4);
        assertThat(fraction1.subtract(fraction2).toString()).isEqualTo("1/2");
    }
    
    @Test
    public void givenTwoFractionInstances_whenCalledmultiply_thenCorrect() {
        Fraction fraction1 = Fraction.getFraction(3, 4);
        Fraction fraction2 = Fraction.getFraction(1, 4);
        assertThat(fraction1.multiplyBy(fraction2).toString()).isEqualTo("3/16");
    }
}
