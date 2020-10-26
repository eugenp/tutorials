package com.baeldung.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;

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

        int sum = 0, count = 0;

        System.out.println("Please enter your college degrees. To finish, enter baeldung website url");

        while (scanner.hasNextInt()) {
            int nmbr = scanner.nextInt();
            sum += nmbr;
            count++;
        }
        int mean = sum / count;

        System.out.println("Your average degree is " + mean);

        if (scanner.hasNext(Pattern.compile("www.baeldung.com")))
            System.out.println("Correct!");
        else
            System.out.println("Baeldung website url is www.baeldung.com");

        if (scanner != null)
            scanner.close();

    }

}
