package com.baeldung.area.circle;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CircleArea {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                double radius = Double.parseDouble(args[0]);
                calculateArea(radius);
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid value for radius");
                System.exit(0);
            }
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter radius value: ");
            double radius = scanner.nextDouble();
            calculateArea(radius);
        } catch (InputMismatchException e) {
            System.out.println("Invalid value for radius");
            System.exit(0);
        }

        Circle circle = new Circle(7);
        System.out.println(circle);
    }

    private static void calculateArea(double radius) {
        double area = radius * radius * Math.PI;
        System.out.println("The area of the circle [radius = " + radius + "]: " + area);
    }
}
