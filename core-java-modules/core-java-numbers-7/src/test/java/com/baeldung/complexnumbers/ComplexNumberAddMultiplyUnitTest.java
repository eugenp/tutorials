package com.baeldung.complexnumbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ComplexNumber {
    public long real;
    public long imaginary;

    public ComplexNumber(long a, long b) {
        this.real = a;
        this.imaginary = b;
    }

    public ComplexNumber(String complexNumberStr) {

        Pattern pattern = Pattern.compile("(-?\\d+)?(?:([+-]?\\d+)i)?");
        Matcher matcher = pattern.matcher(complexNumberStr.replaceAll("\\s", ""));

        if (matcher.matches()) {
            // Extract real and imaginary parts
            String realPartStr = matcher.group(1);
            String imaginaryPartStr = matcher.group(2);

            // Parse real part (if present)
            real = (realPartStr != null) ? Long.parseLong(realPartStr) : 0;

            // Parse imaginary part (if present)
            imaginary = (imaginaryPartStr != null) ? Long.parseLong(imaginaryPartStr) : 0;
        } else {
            throw new IllegalArgumentException("Invalid complex number format, supported format is `a+bi`");
        }
    }

    public long getReal() {
        return real;
    }

    public long getImaginary() {
        return imaginary;
    }

    public String toString() {
        return real + "+" + imaginary + "i";
    }

    public ComplexNumber add(ComplexNumber that) {
        return new ComplexNumber(real + that.getReal(), imaginary + that.getImaginary());
    }

    public ComplexNumber multiply(ComplexNumber that) {
        long newReal = this.real * that.real - this.imaginary * that.imaginary;
        long newImaginary = this.real * that.imaginary + this.imaginary * that.real;
        return new ComplexNumber(newReal, newImaginary);
    }

    public boolean isSame(ComplexNumber that) {
        return this.real == that.real && this.imaginary == that.imaginary;
    }

}

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
        Assertions.assertTrue(multiplied.isSame(expected));
    }

}
