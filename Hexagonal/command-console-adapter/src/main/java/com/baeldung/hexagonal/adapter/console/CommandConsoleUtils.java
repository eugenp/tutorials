package com.baeldung.hexagonal.adapter.console;

import java.util.Scanner;

import com.baeldung.hexagonal.domain.Customer;

public class CommandConsoleUtils {
    
    public static String readString(Scanner scanner) {
        System.out.print( "> " );
        return scanner.next();
      }
    
    
    public static void printCustomtoer(Customer c) {
        
        System.out.println("Customer Id: " + c.getCustomerId());
        System.out.println("Customer Name: " + c.getFirstName() +  " " + c.getLastName());
        System.out.println("Customer Points: " + c.getRewardedpoints());
        System.out.println("Customer Status: " + c.getStatus());
    }
    
    public static void printMainmenu() {
        System.out.println("LS : list all customers");
        System.out.println("REG : register a customer");
        System.out.println("UG : upgrade a customer");
        System.out.println("DG : downgrade a customer");
        System.out.println("INFO : show main menu command");
        System.out.println("EXIT : exit application");
    }

}
