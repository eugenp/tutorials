package com.baeldung.dddhexagonalapp.cli.adaptor;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;
import com.baeldung.dddhexagonalapp.coreapp.service.CardHolderService;
import com.google.inject.Inject;

public class CmdLineInterfaceImpl{

    private CardHolderService cardHolderService;

    @Inject
    public CmdLineInterfaceImpl(CardHolderService cardHolderService) {
        this.cardHolderService = cardHolderService;
    }

    public void list(Scanner scanner) {
        System.out.println("All card holders registered so far");

        List<CardHolder> allCardHolders = cardHolderService.getAllCardHolders();

        allCardHolders.stream()
            .forEach(c -> {

                CmdLineUtils.printCardHolder(c);
                System.out.println("------------------------------------");
            });

    }

    public void register(Scanner scanner) {
        System.out.println("What is the first name ? ");
        String firstName = CmdLineUtils.readString(scanner);

        System.out.println("What is the last name ? ");
        String lastName = CmdLineUtils.readString(scanner);

        CardHolder unregistered = new CardHolder();
        unregistered.setFirstName(firstName.toUpperCase());
        unregistered.setLastName(lastName.toUpperCase());

        Random random = new Random();
        String creditCardNumber = String.format("%04d", random.nextInt(10000)) + " " + String.format("%04d", random.nextInt(10000)) + " " + String.format("%04d", random.nextInt(10000)) + " " + String.format("%04d", random.nextInt(10000));
        unregistered.setCreditCardNumber(creditCardNumber);

        CardHolder registered = cardHolderService.registerCardHolder(unregistered);

        System.out.println("New card holder registered successfully with the following : ");
        CmdLineUtils.printCardHolder(registered);

    }

    public void info() {
        CmdLineUtils.printMainmenu();
    }

}
