package com.baeldung.dddhexagonalapp.cli.adaptor;

import java.util.List;
import java.util.Scanner;

import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;
import com.baeldung.dddhexagonalapp.coreapp.service.CardHolderService;
import com.baeldung.dddhexagonalapp.coreapp.service.CardHolderServiceImpl;

public class CmdLineConsole {

    private CardHolderService cardHolderService = new CardHolderServiceImpl();

    private static String readString(Scanner scanner) {
        System.out.print("$ ");
        return scanner.next();
    }

    public void list(Scanner scanner) {
        System.out.println("All card holders registered so far");

        List<CardHolder> allCardHolders = cardHolderService.getAllCardHolders();

        allCardHolders.stream()
            .forEach(c -> {
                System.out.println(c + "\n------------------------------------");
            });
    }

    public void register(Scanner scanner) {
        System.out.println("What is the first name ? ");
        String firstName = readString(scanner).toUpperCase();

        System.out.println("What is the last name ? ");
        String lastName = readString(scanner).toUpperCase();

        CardHolder unregistered = new CardHolder(firstName, lastName);
        CardHolder registered = cardHolderService.registerCardHolder(unregistered);

        System.out.println("New card holder registered successfully with the following : \n" + registered);
    }

    public void info() {
        System.out.println("ALL : Display list of all card holders ");
        System.out.println("REGISTER : Register a new card holder");
        System.out.println("INFO : Show main menu command");
        System.out.println("EXIT : Exit application");
    }

}
