package com.baeldung.spring.spel.examples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("spelArithmetic")
public class SpelArithmetic {
    @Value("#{19 + 1}")
    private double add;

    @Value("#{'Some string ' + 'plus other string'}")
    private String addString;

    @Value("#{20 - 1}")
    private double subtract;

    @Value("#{10 * 2}")
    private double multiply;

    @Value("#{36 / 2}")
    private double divide;
    @Value("#{36 div 2}")
    private double divideAlphabetic;

    @Value("#{37 % 10}")
    private double modulo;
    @Value("#{37 mod 10}")
    private double moduloAlphabetic;

    @Value("#{2 ^ 9}")
    private double powerOf;

    @Value("#{(2 + 2) * 2 + 9}")
    private double brackets;

    public double getAdd() {
        return add;
    }

    public void setAdd(double add) {
        this.add = add;
    }

    public String getAddString() {
        return addString;
    }

    public void setAddString(String addString) {
        this.addString = addString;
    }

    public double getSubtract() {
        return subtract;
    }

    public void setSubtract(double subtract) {
        this.subtract = subtract;
    }

    public double getMultiply() {
        return multiply;
    }

    public void setMultiply(double multiply) {
        this.multiply = multiply;
    }

    public double getDivide() {
        return divide;
    }

    public void setDivide(double divide) {
        this.divide = divide;
    }

    public double getDivideAlphabetic() {
        return divideAlphabetic;
    }

    public void setDivideAlphabetic(double divideAlphabetic) {
        this.divideAlphabetic = divideAlphabetic;
    }

    public double getModulo() {
        return modulo;
    }

    public void setModulo(double modulo) {
        this.modulo = modulo;
    }

    public double getModuloAlphabetic() {
        return moduloAlphabetic;
    }

    public void setModuloAlphabetic(double moduloAlphabetic) {
        this.moduloAlphabetic = moduloAlphabetic;
    }

    public double getPowerOf() {
        return powerOf;
    }

    public void setPowerOf(double powerOf) {
        this.powerOf = powerOf;
    }

    public double getBrackets() {
        return brackets;
    }

    public void setBrackets(double brackets) {
        this.brackets = brackets;
    }

    @Override
    public String toString() {
        return "SpelArithmetic{" +
                "add=" + add +
                ", addString='" + addString + '\'' +
                ", subtract=" + subtract +
                ", multiply=" + multiply +
                ", divide=" + divide +
                ", divideAlphabetic=" + divideAlphabetic +
                ", modulo=" + modulo +
                ", moduloAlphabetic=" + moduloAlphabetic +
                ", powerOf=" + powerOf +
                ", brackets=" + brackets +
                '}';
    }
}
