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

    public ComplexNumber(String complexNumberStr) {
        Pattern pattern = Pattern.compile("(-?\\d+)\\s*[+]\\s*(-?\\d+)\\s*i|(-?\\d+)\\s*i\\s*[+]\\s*(-?\\d+)");
        Matcher matcher = pattern.matcher(complexNumberStr);
        if (matcher.find()) {
            // Extract real and imaginary parts
            long realPart;
            long imaginaryPart;
            if (matcher.group(1) != null && matcher.group(2) != null) {
                realPart = Long.parseLong(matcher.group(1)); // Group 1 is the real part
                imaginaryPart = Long.parseLong(matcher.group(2)); // Group 2 is the imaginary part
            } else {
                realPart = Long.parseLong(matcher.group(4)); // Group 4 is the real part
                imaginaryPart = Long.parseLong(matcher.group(3)); // Group 3 is the imaginary part
            }
            this.real = realPart;
            this.imaginary = imaginaryPart;
        } else {
            throw new IllegalArgumentException("String does not match the complex number pattern of `a+bi` or `bi+a`.");
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
            "1+1i, 1+1i, 0+2i",
            "  3+2i, 1 +    7i,   -11 + 23i   ",
            "0+5i, 3+0i, 0+15i",
            "0+0i, -2+0i, 0+0i",
            "-3+2i, 1-7i, -23-23i"
    })
    public void sum_two_complex_numbers(String complexStr1, String complexStr2, String expectedStr) {
        ComplexNumber complex1 = new ComplexNumber(complexStr1);
        ComplexNumber complex2 = new ComplexNumber(complexStr2);
        ComplexNumber expected = new ComplexNumber(expectedStr);
        ComplexNumber multiplied = complex1.multiply(complex2);
        System.out.println(multiplied.toString());
        Assertions.assertTrue(multiplied.isSame(expected));
    }

}
