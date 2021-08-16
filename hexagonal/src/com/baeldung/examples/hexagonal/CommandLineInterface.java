package com.baeldung.examples.hexagonal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class CommandLineInterface {
    private final AccountService accountService;

    public CommandLineInterface(AccountService accountService) {
        this.accountService = accountService;
    }

    public void start() throws IOException {
        while (true) {
            String[] args = getArgs();
            if (args == null) return;
            String firstArg = args[0];

            switch (firstArg) {
                case "create":
                    createHandler();
                    break;
                case "balance":
                    balanceHandler(args);
                    break;
                case "deposit":
                    depositHandler(args);
                    break;
                case "withdrawal":
                    withdrawalHandler(args);
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private String[] getArgs() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String argsString = reader.readLine();
        String[] args = argsString.split(" ");
        if (args.length < 1) {
            System.out.println("Invalid command");
            return null;
        }
        return args;
    }

    private void createHandler() {
        UUID account = accountService.createAccount();
        System.out.println(account);
    }

    private void balanceHandler(String[] args) {
        UUID uuid = UUID.fromString(args[1]);
        Double balance = accountService.getBalance(uuid);
        System.out.println(balance);
    }

    private void depositHandler(String[] args) {
        Double amount = Double.parseDouble(args[2]);
        UUID uuid = UUID.fromString(args[1]);
        Double deposit = accountService.deposit(uuid, amount);
        System.out.println(deposit);
    }

    private void withdrawalHandler(String[] args) {
        Double amount = Double.parseDouble(args[2]);
        UUID uuid = UUID.fromString(args[1]);
        Double withdrawal = accountService.withdrawal(uuid, amount);
        System.out.println(withdrawal);
    }
}
