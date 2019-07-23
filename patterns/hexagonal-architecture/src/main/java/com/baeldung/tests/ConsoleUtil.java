package com.baeldung.tests;

import java.util.Scanner;

import com.baeldung.appcore.domain.Customer;

public class ConsoleUtil {
    public static String readString(Scanner scanner) {
        System.out.print( "> " );
        return scanner.next();
    }

    public static void logCustomerToConsole(Customer c) {
        System.out.println("Customer Id: " + c.getCustomerId());
        System.out.println("Customer Name: " + c.getFirstName() +  " " + c.getLastName());
    }

    public static void printMainMenu() {
        System.out.println("LS : list all customers");
        System.out.println("REG : register a customer");
        System.out.println("UPD : update a customer");
        System.out.println("INFO : show main menu command");
        System.out.println("EXIT : exit application");
    }
}