package com.baeldung.complexnumbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

    public ComplexNumber(String complexNumberStr1) {

        String complexNumberStr = complexNumberStr1.replaceAll("\\s", "");
        if (complexNumberStr.contains("i")) {
            // Split the string based on '+' or '-'
            String[] parts = complexNumberStr.split("(?=[+-])");

            if (parts.length == 1) {
                // Only real part (no imaginary part)
                if (complexNumberStr.endsWith("i")) {
                    real = 0;
                    imaginary = Long.parseLong(parts[0].substring(0, parts[0].length() - 1));
                } else {
                    real = Long.parseLong(complexNumberStr);
                    imaginary = 0;
                }
            } else if (parts.length == 2) {
                // Both real and imaginary parts present
                real = Long.parseLong(parts[0]);
                String imaginaryString = parts[1].substring(0, parts[1].length() - 1); // Remove 'i'
                if (imaginaryString.isEmpty()) {
                    // Only 'i' without coefficient, hence imaginary part is 1
                    imaginary = 1;
                } else {
                    imaginary = Long.parseLong(imaginaryString);
                }
            } else {
                throw new IllegalArgumentException("Invalid complex number format");
            }
        } else {
            // Only real part without 'i'
            real = Long.parseLong(complexNumberStr);
            imaginary = 0;
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
