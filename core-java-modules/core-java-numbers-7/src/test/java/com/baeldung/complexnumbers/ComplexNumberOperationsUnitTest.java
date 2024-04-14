package com.baeldung.complexnumbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ComplexNumberOperationsUnitTest {

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
    public void givenTwoComplexNumbers_multiplyAndGetResult(String complexStr1, String complexStr2, String expectedStr) {
        ComplexNumber complex1 = ComplexNumber.fromString(complexStr1);
        ComplexNumber complex2 = ComplexNumber.fromString(complexStr2);
        ComplexNumber expected = ComplexNumber.fromString(expectedStr);
        ComplexNumber product = complex1.multiply(complex2);
        Assertions.assertTrue(isSame(product, expected));
    }

    @ParameterizedTest(name = "Adding {0} and {1}")
    @CsvSource({
            "3+2i, 1+7i, 4+9i",
            "2, 4, 6",
            "2, 4i, 2+4i",
            "1+1i, 1+1i, 2+2i",
            "  3+2i, 1 +    7i,   4 + 9i   ",
            "0+5i, 3+0i, 3+5i",
            "0+0i, -2+0i, -2+0i",
            "-3+2i, 1-7i, -2-5i",
            "2+4i, 0, 2+4i"
    })
    public void givenTwoComplexNumbers_addThemAndGetResult(String complexStr1, String complexStr2, String expectedStr) {
        ComplexNumber complex1 = ComplexNumber.fromString(complexStr1);
        ComplexNumber complex2 = ComplexNumber.fromString(complexStr2);
        ComplexNumber expected = ComplexNumber.fromString(expectedStr);
        ComplexNumber sum = complex1.add(complex2);
        Assertions.assertTrue(isSame(sum, expected));
    }

    @ParameterizedTest(name = "Subtracting {0} and {1}")
    @CsvSource({
            "3+2i, 1+7i, 2-5i",
            "2, 4, -2",
            "2, 4i, 2-4i",
            "1+1i, 1+1i, 0",
            "  3+ 2i, 1+  7i, 2-5i",
            "0+5i, 3+0i, -3+5i",
            "0+0i, -2+0i, 2+0i",
            "-3+2i, 1-7i, -4+9i",
            "2+4i, 0, 2+4i"
    })
    public void givenTwoComplexNumbers_subtractAndGetResult(String complexStr1, String complexStr2, String expectedStr) {
        ComplexNumber complex1 = ComplexNumber.fromString(complexStr1);
        ComplexNumber complex2 = ComplexNumber.fromString(complexStr2);
        ComplexNumber expected = ComplexNumber.fromString(expectedStr);
        ComplexNumber sum = complex1.subtract(complex2);
        Assertions.assertTrue(isSame(sum, expected));
    }

    @ParameterizedTest(name = "Dividing {0} and {1}")
    @CsvSource({
            "3+2i, 1+7i, 0.34-0.38i",
            "2, 4, 0.5",
            "2, 4i, 0-0.5i",
            "1+1i, 1+1i, 1",
            "3 +   2i, 1  +   7i, 0.34-0.38i",
            "0+5i, 3+0i, 0+1.6666666666666667i",
            "0+0i, -2+0i, 0+0i",
            "-3+2i, 1-7i, -0.34-0.38i",
            "2+4i, 1, 2+4i"
    })
    public void givenTwoComplexNumbers_divideThemAndGetResult(String complexStr1, String complexStr2, String expectedStr) {
        ComplexNumber complex1 = ComplexNumber.fromString(complexStr1);
        ComplexNumber complex2 = ComplexNumber.fromString(complexStr2);
        ComplexNumber expected = ComplexNumber.fromString(expectedStr);
        ComplexNumber sum = complex1.divide(complex2);
        Assertions.assertTrue(isSame(sum, expected));
    }

    @Test
    public void givenAComplexNumberAsZero_handleDivideByZeroScenario() {
        ComplexNumber complex1 = new ComplexNumber(1, 1);
        ComplexNumber zero = new ComplexNumber(0, 0);
        Exception exception = Assertions.assertThrows(ArithmeticException.class, () -> {
            complex1.divide(zero);
        });
        Assertions.assertEquals(exception.getMessage(), "Division by 0 is not allowed!");
    }

    public boolean isSame(ComplexNumber result, ComplexNumber expected) {
        return result.real() == expected.real() && result.imaginary() == expected.imaginary();
    }
}
