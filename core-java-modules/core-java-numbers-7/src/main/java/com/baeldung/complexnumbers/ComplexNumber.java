package com.baeldung.complexnumbers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexNumber {
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

}