package com.baeldung.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleSystemIn {
    
    public static void main(String[] args)
    {
        System.out.println("Please enter number of years of experience as a developer: ");
        
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
        
        System.out.println("You are a " + (i > 5? "great" : "good") + " developer!");
    }

}
