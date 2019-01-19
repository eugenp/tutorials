package com.baeldung.objects;

public class Calculator {

    private double result;

    public Calculator() {
        result = 0;
    }

    public Calculator(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double add(double d1, double d2) {
        result = d1 + d2;
        return result;
    }

    public double substract(double d1, double d2) {
        result = d1 - d2;
        return result;
    }

    public double multiply(double d1, double d2) {
        result = d1 * d2;
        return result;
    }

    public double divide(double d1, double d2) {
        result = d1 / d2;
        return result;
    }

    public void clear() {
        this.result = 0;
    }

    public void printResult() {
        System.out.println("Result: " + result);
    }

    public static void main(String... args) {
        Calculator calc = new Calculator();
        calc.printResult();

        calc.add(2, 3);
        calc.printResult();

        calc.substract(3, 2);
        calc.printResult();

        calc.multiply(2, 3);
        calc.printResult();

        calc.divide(6, 2);
        calc.printResult();

        calc.clear();
        calc.printResult();
    }
}
