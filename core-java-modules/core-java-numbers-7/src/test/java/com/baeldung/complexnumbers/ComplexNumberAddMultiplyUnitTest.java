package com.baeldung.complexnumbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ComplexNumberAddMultiplyUnitTest {

    @ParameterizedTest(name = "Multiplying {0} and {1}")
    @CsvSource({
            "3+2i, 1+7i, -11+23i",
            "2, 4, 8",
            "2, 4i, 8i",
            "1+1i, 1+1i, 0+2i",
            "  3+2i, 1 +    7i,   -11 + 23i   ",
            "0+5i, 3+0i, 0+15i",
            "0+0i, -2+0i, 0+0i",
            "-3+2i, 1-7i, 11+23i",
            "2+4i, 0, 0"
    })
    public void sum_two_complex_numbers(String complexStr1, String complexStr2, String expectedStr) {
        ComplexNumber complex1 = new ComplexNumber(complexStr1);
        ComplexNumber complex2 = new ComplexNumber(complexStr2);
        ComplexNumber expected = new ComplexNumber(expectedStr);
        ComplexNumber multiplied = complex1.multiply(complex2);
        Assertions.assertTrue(isSame(multiplied, expected));
    }

    public boolean isSame(ComplexNumber result, ComplexNumber expected) {
        return result.real == expected.real && result.imaginary == expected.imaginary;
    }
}
