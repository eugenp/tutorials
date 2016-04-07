package com.baeldung.spring.spel.examples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("spelOperators")
public class SpelOperators {

    @Value("#{1 == 1}")
    private boolean equal;

    @Value("#{1 != 1}")
    private boolean notEqual;

    @Value("#{1 < 1}")
    private boolean lessThan;

    @Value("#{1 <= 1}")
    private boolean lessThanOrEqual;

    @Value("#{1 > 1}")
    private boolean greaterThan;

    @Value("#{someCar.engine.numberOfCylinders >= 6}")
    private boolean greaterThanOrEqual;

    @Value("#{someCar.horsePower == 250 and someCar.engine.capacity < 4000}")
    private boolean and;

    @Value("#{someCar.horsePower > 300 or someCar.engine.capacity > 3000}")
    private boolean or;

    @Value("#{!(someCar.engine.numberOfCylinders == 6)}")
    private boolean not;

    @Value("#{1 + 1}")
    private double add;

    @Value("#{someCar.model + ' manufactored by ' + someCar.make}")
    private String addString;

    @Value("#{1 - 1}")
    private double subtraction;

    @Value("#{1 * 1}")
    private double multiply;

    @Value("#{10 / 2}")
    private double divide;

    @Value("#{37 % 10}")
    private double modulo;

    @Value("#{2 ^ 2}")
    private double powerOf;

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public boolean isNotEqual() {
        return notEqual;
    }

    public void setNotEqual(boolean notEqual) {
        this.notEqual = notEqual;
    }

    public boolean isLessThan() {
        return lessThan;
    }

    public void setLessThan(boolean lessThan) {
        this.lessThan = lessThan;
    }

    public boolean isLessThanOrEqual() {
        return lessThanOrEqual;
    }

    public void setLessThanOrEqual(boolean lessThanOrEqual) {
        this.lessThanOrEqual = lessThanOrEqual;
    }

    public boolean isGreaterThan() {
        return greaterThan;
    }

    public void setGreaterThan(boolean greaterThan) {
        this.greaterThan = greaterThan;
    }

    public boolean isGreaterThanOrEqual() {
        return greaterThanOrEqual;
    }

    public void setGreaterThanOrEqual(boolean greaterThanOrEqual) {
        this.greaterThanOrEqual = greaterThanOrEqual;
    }

    public boolean isAnd() {
        return and;
    }

    public void setAnd(boolean and) {
        this.and = and;
    }

    public boolean isOr() {
        return or;
    }

    public void setOr(boolean or) {
        this.or = or;
    }

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

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

    public double getSubtraction() {
        return subtraction;
    }

    public void setSubtraction(double subtraction) {
        this.subtraction = subtraction;
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

    public double getModulo() {
        return modulo;
    }

    public void setModulo(double modulo) {
        this.modulo = modulo;
    }

    public double getPowerOf() {
        return powerOf;
    }

    public void setPowerOf(double powerOf) {
        this.powerOf = powerOf;
    }

    @Override
    public String toString() {
        return "[equal=" + equal + ", " +
                "notEqual=" + notEqual + ", " +
                "lessThan=" + lessThan + ", " +
                "lessThanOrEqual=" + lessThanOrEqual + ", " +
                "greaterThan=" + greaterThan + ", " +
                "greaterThanOrEqual=" + greaterThanOrEqual + ", " +
                "and=" + and + ", " +
                "or=" + or + ", " +
                "not=" + not + ", " +
                "add=" + add + ", " +
                "addString=" + addString + ", " +
                "subtraction=" + subtraction + ", " +
                "multiply=" + multiply + ", " +
                "divide=" + divide + ", " +
                "modulo=" + modulo + ", " +
                "powerOf=" + powerOf + "]";
    }
}
