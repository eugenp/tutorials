package com.baeldung.complexnumbers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record ComplexNumber(double real, double imaginary) {

    public static ComplexNumber fromString(String complexNumberStr) {

        Pattern pattern = Pattern.compile("(-?\\d*\\.?\\d+)?(?:([+-]?\\d*\\.?\\d+)i)?");
        Matcher matcher = pattern.matcher(complexNumberStr.replaceAll("\\s", ""));

        if (matcher.matches()) {
            // Extract real and imaginary parts
            String realPartStr = matcher.group(1);
            String imaginaryPartStr = matcher.group(2);

            // Parse real part (if present)
            double real = (realPartStr != null) ? Double.parseDouble(realPartStr) : 0;

            // Parse imaginary part (if present)
            double imaginary = (imaginaryPartStr != null) ? Double.parseDouble(imaginaryPartStr) : 0;
            return new ComplexNumber(real, imaginary);
        } else {
            throw new IllegalArgumentException("Invalid complex number format(" + complexNumberStr + "), supported format is `a+bi`");
        }
    }

    public String toString() {
        return real + "+" + imaginary + "i";
    }

    public ComplexNumber add(ComplexNumber that) {
        return new ComplexNumber(real + that.real, imaginary + that.imaginary);
    }

    public ComplexNumber multiply(ComplexNumber that) {
        double newReal = this.real * that.real - this.imaginary * that.imaginary;
        double newImaginary = this.real * that.imaginary + this.imaginary * that.real;
        return new ComplexNumber(newReal, newImaginary);
    }

    public ComplexNumber subtract(ComplexNumber that) {
        return new ComplexNumber(real - that.real, imaginary - that.imaginary);
    }

    public ComplexNumber divide(ComplexNumber that) {
        if(that.real == 0 && that.imaginary == 0 ){
            throw new ArithmeticException("Division by 0 is not allowed!");
        }
        double c2d2 = Math.pow(that.real, 2) + Math.pow(that.imaginary, 2);
        double newReal = (this.real * that.real + this.imaginary * that.imaginary) / c2d2;
        double newImaginary = (this.imaginary * that.real - this.real * that.imaginary) / c2d2;
        return new ComplexNumber(newReal, newImaginary);
    }

}