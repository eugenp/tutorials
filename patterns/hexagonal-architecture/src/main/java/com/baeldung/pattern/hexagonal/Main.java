package com.baeldung.pattern.hexagonal;

import java.util.Scanner;

public class Main {
    private CommandLineAdapter adapter;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Main main = new Main();
        boolean isContinue = true;
        while (isContinue) {
            System.out.print("$ ");
            String line = in.nextLine();
            if(!line.equalsIgnoreCase("exit")){
                System.out.println(main.execute(line));
            } else {
                isContinue = false;
            }
        }
        in.close();
    }

    private Main() {
        // initialize the adapter to persistence
        StockTradeBookPersistence persistence = new PersistenceFileAdapter("order.csv");

        // initialize the business logic, and inject the adapter
        StockBrokerImpl stockBroker = new StockBrokerImpl();
        stockBroker.setPersistence(persistence);

        // initialize the adapter to command line, and inject the business logic
        adapter = new CommandLineAdapter();
        adapter.setStockBroker(stockBroker);
    }

    private String execute(String line) {
        String[] input = line.split(" ");
        if (input.length != 4) {
            return "Invalid command or parameter";
        } else {
            return adapter.execute(input[0], input[1], input[2], input[3]);
        }
    }
}
