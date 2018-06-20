package com.baeldung.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleScannerClass {

    public static void main(String[] args) {
        System.out.println("Please enter your name and surname: ");

        Scanner scanner = new Scanner(System.in);

        String nameSurname = scanner.nextLine();

        System.out.println("Please enter your gender: ");

        char gender = scanner.next().charAt(0);

        System.out.println("Please enter your age: ");

        int age = scanner.nextInt();

        System.out.println("Please enter your height in meters: ");

        double height = scanner.nextDouble();

        System.out.println(nameSurname + ", " + age + ", is a great " + (gender == 'm' ? "guy" : "girl") + " with " + height + " meters height" + " and " + (gender == 'm' ? "he" : "she") + " reads Baeldung.");

        System.out.print("Have a good");
        System.out.print(" one!");

        System.out.println("\nPlease enter number of years of experience as a developer: ");

        BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in));

        int i = 0;

        try {
            i = Integer.parseInt(buffReader.readLine());
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("You are a " + (i > 5 ? "great" : "good") + " developer!");

        if (scanner != null)
            scanner.close();

    }

}
