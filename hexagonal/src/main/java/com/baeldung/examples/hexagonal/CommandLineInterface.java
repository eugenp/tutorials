package main.java.com.baeldung.examples.hexagonal;

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String argsString = reader.readLine();
            String[] args = argsString.split(" ");
            if (args.length < 1) {
                System.out.println("Invalid command");
                return;
            }
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

    private void createHandler() {
        UUID account = accountService.createAccount();
        System.out.println(account);
    }

    private void balanceHandler(String[] args) {
        var uuid = UUID.fromString(args[1]);
        var balance = accountService.getBalance(uuid);
        System.out.println(balance);
    }

    private void depositHandler(String[] args) {
        var amount = Double.parseDouble(args[2]);
        var uuid = UUID.fromString(args[1]);
        var deposit = accountService.deposit(uuid, amount);
        System.out.println(deposit);
    }

    private void withdrawalHandler(String[] args) {
        var amount = Double.parseDouble(args[2]);
        var uuid = UUID.fromString(args[1]);
        var withdrawal = accountService.withdrawal(uuid, amount);
        System.out.println(withdrawal);
    }
}
