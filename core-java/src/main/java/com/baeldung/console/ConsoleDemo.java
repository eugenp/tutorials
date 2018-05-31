package com.baeldung.console;

import java.util.Scanner;

public class ConsoleDemo {
    
    public static void main(String[] args)
    {
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
    
        if(scanner!=null)
            scanner.close();
    }

}
