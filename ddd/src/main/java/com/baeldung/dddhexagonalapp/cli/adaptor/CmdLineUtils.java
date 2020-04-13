package com.baeldung.dddhexagonalapp.cli.adaptor;

import java.util.Scanner;

import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;

public class CmdLineUtils {

    public static String readString(Scanner scanner) {
        System.out.print("$ ");
        return scanner.next();
    }

    public static void printCardHolder(CardHolder c) {

        System.out.println("Card holder ID: " + c.getCardHolderId());
        System.out.println("Card holder name: " + c.getFirstName() + " " + c.getLastName());
        System.out.println("Card Limit: " + c.getCreditCardLimit() + " USD");
        System.out.println("Card Category: " + c.getStatus());
        System.out.println("Credit card number: " + c.getCreditCardNumber());
    }

    public static void printMainmenu() {
        System.out.println("ALL : Display list of all card holders");
        System.out.println("REGISTER : Register a new card holder");
        System.out.println("UPGRADE : Upgrade card category");
        System.out.println("DOWNGRADE : Downgrade card category");
        System.out.println("INFO : Show main menu command");
        System.out.println("EXIT : Exit application");
    }

}
