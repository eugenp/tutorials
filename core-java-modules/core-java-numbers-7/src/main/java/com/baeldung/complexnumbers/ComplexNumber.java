package com.baeldung.complexnumbers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexNumber {
    public double real;
    public double imaginary;

    public ComplexNumber(double a, double b) {
        this.real = a;
        this.imaginary = b;
    }

    public ComplexNumber(String complexNumberStr) {

        Pattern pattern = Pattern.compile("(-?\\d*\\.?\\d+)?(?:([+-]?\\d*\\.?\\d+)i)?");
        Matcher matcher = pattern.matcher(complexNumberStr.replaceAll("\\s", ""));

        if (matcher.matches()) {
            // Extract real and imaginary parts
            String realPartStr = matcher.group(1);
            String imaginaryPartStr = matcher.group(2);

            // Parse real part (if present)
            real = (realPartStr != null) ? Double.parseDouble(realPartStr) : 0;

            // Parse imaginary part (if present)
            imaginary = (imaginaryPartStr != null) ? Double.parseDouble(imaginaryPartStr) : 0;
        } else {
            throw new IllegalArgumentException("Invalid complex number format(" + complexNumberStr + "), supported format is `a+bi`");
        }
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public String toString() {
        return real + "+" + imaginary + "i";
    }

    public ComplexNumber add(ComplexNumber that) {
        return new ComplexNumber(real + that.getReal(), imaginary + that.getImaginary());
    }

    public ComplexNumber multiply(ComplexNumber that) {
        double newReal = this.real * that.real - this.imaginary * that.imaginary;
        double newImaginary = this.real * that.imaginary + this.imaginary * that.real;
        return new ComplexNumber(newReal, newImaginary);
    }

    public ComplexNumber subtract(ComplexNumber that) {
        return new ComplexNumber(real - that.getReal(), imaginary - that.getImaginary());
    }

    public ComplexNumber divide(ComplexNumber that) {
        double c2d2 = Math.pow(that.real, 2) + Math.pow(that.imaginary, 2);
        double newReal = (this.real * that.real + this.imaginary * that.imaginary) / c2d2;
        double newImaginary = (this.imaginary * that.real - this.real * that.imaginary) / c2d2;
        return new ComplexNumber(newReal, newImaginary);
    }

}