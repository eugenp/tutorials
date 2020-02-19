package com.baeldung.hexagonalarchitecture.adapters.driver;

import com.baeldung.hexagonalarchitecture.application.ports.CalculatorPort;

public class ConsoleCalculator { 
        private CalculatorPort calculator;
        ConsoleCalculator(CalculatorPort calculatorCore){
                calculator = calculatorCore;
        }

        public void getAreaCommand() {
            System.out.println("Please enter your shape: ");
            String shapeName = System.console().readLine();

            switch (shapeName) {
            case ("triangle"):
                System.out.println("Please enter sides size: ");
                double A = Double.parseDouble(System.console().readLine());
                double B = Double.parseDouble(System.console().readLine());
                double C = Double.parseDouble(System.console().readLine());
                System.out.println("Area is: " + calculator.triangleAreaBySides(A, B, C));
                break;
            case ("square"):
                System.out.println("Please enter base and height size: ");
                double base = Double.parseDouble(System.console().readLine());
                double height = Double.parseDouble(System.console().readLine());
                System.out.println("Area is: " + calculator.squareArea(base, height));
                break; 
            }
        }
}
