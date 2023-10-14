package com.baeldung.multinput;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MultiInputs {
    public void UsingSpaceDelimiter(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter two numbers: ");
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        System.out.println("You entered " + num1 + " and " + num2);

    }
    public void UsingREDelimiter(){
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("[\\s,]+");
        System.out.print("Enter two numbers separated by a space or a comma: ");
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        System.out.println("You entered " + num1 + " and " + num2);

    }
    public void UsingCustomDelimiter(){
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter(";");
        System.out.print("Enter two numbers separated by a semicolon: ");
        try { int num1 = scanner.nextInt();
            int num2 = scanner.nextInt();
            System.out.println("You entered " + num1 + " and " + num2); }
        catch (InputMismatchException e)
        { System.out.println("Invalid input. Please enter two integers separated by a semicolon."); }

    }
}

