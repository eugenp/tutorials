package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.service.Calculator;

public class CLIApplication {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        Double a = Double.parseDouble(args[0]);
        Double b = Double.parseDouble(args[2]);
        String operation = args[1];

        switch (operation) {
            case "+":
                System.out.printf("%s + %s = %s", a, b, calculator.add(a, b));
                break;
            case "/":
                System.out.printf("%s / %s = %s", a, b, calculator.divide(a, b));
                break;
            default:
                System.out.printf("Invalid operation %s", operation);
        }
    }
}
