package com.baeldung.dddhexagonalapp.cli.adaptor;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;
import com.baeldung.dddhexagonalapp.coreapp.service.CardHolderService;

public class CmdLineConsole {

    private CardHolderService cardHolderService = new CardHolderService();

    public void list(Scanner scanner) {
        System.out.println("All card holders registered so far");

        List<CardHolder> allCardHolders = cardHolderService.getAllCardHolders();

        allCardHolders.stream()
            .forEach(c -> {

                System.out.println(c);
                System.out.println("------------------------------------");
            });

    }

    public void register(Scanner scanner) {
        System.out.println("What is the first name ? ");
        String firstName = readString(scanner);

        System.out.println("What is the last name ? ");
        String lastName = readString(scanner);

        CardHolder unregistered = new CardHolder();
        unregistered.setFirstName(firstName.toUpperCase());
        unregistered.setLastName(lastName.toUpperCase());

        Random random = new Random();
        String creditCardNumber = String.format("%04d", random.nextInt(10000)) + " " + String.format("%04d", random.nextInt(10000)) + " " + String.format("%04d", random.nextInt(10000)) + " " + String.format("%04d", random.nextInt(10000));
        unregistered.setCreditCardNumber(creditCardNumber);

        CardHolder registered = cardHolderService.registerCardHolder(unregistered);

        System.out.println("New card holder registered successfully with the following : ");
        System.out.println(registered);

    }

    public void info() {
        System.out.println("ALL : Display list of all card holders");
        System.out.println("REGISTER : Register a new card holder");
        System.out.println("INFO : Show main menu command");
        System.out.println("EXIT : Exit application");
    }

    private static String readString(Scanner scanner) {
        System.out.print("$ ");
        return scanner.next();
    }

}
