package com.baeldung.hexagonal;

import java.util.Scanner;

import com.baeldung.hexagonal.adapters.ConsoleOrderServiceAdapter;
import com.baeldung.hexagonal.core.OrderGuiceModule;
import com.baeldung.hexagonal.exception.ItemNotFoundException;
import com.baeldung.hexagonal.ports.IOrderService;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class HexagonalClientApp {

    public static void main(String[] args) throws ItemNotFoundException {
        Injector injector = Guice.createInjector(new OrderGuiceModule());
        IOrderService orderService = injector.getInstance(IOrderService.class);
        try (final Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    showMainMenu();
                    String cmd = readString(scanner);
                    ConsoleOrderServiceAdapter consoleOrderServiceAdapter = new ConsoleOrderServiceAdapter(scanner, orderService);
                    if ("1".equals(cmd)) {
                        consoleOrderServiceAdapter.createOrder();
                    } else if ("2".equals(cmd)) {
                        consoleOrderServiceAdapter.findOrderById();
                    } else if ("3".equals(cmd)) {
                        consoleOrderServiceAdapter.updateOrder();
                    } else if ("4".equals(cmd)) {
                        consoleOrderServiceAdapter.cancelOrder();

                    } else if ("5".equals(cmd)) {
                        System.out.println("Bye-bye.");
                        System.exit(0);

                    } else {
                        System.out.println("Unknown command");
                    }
                } catch (Exception e) {
                    System.out.println("There is an error -- " + e.getClass()
                        .getName() + ":" + e.getMessage());
                    System.out.println();

                }
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("");
        System.out.println("### Hexagonal Architecture: Console Order System Demo for ###");
        System.out.println("1. Create a new order");
        System.out.println("2. Get an order");
        System.out.println("3. Update an order");
        System.out.println("4. Cancel an Order");
        System.out.println("5. Exit");
    }

    private static String readString(Scanner scanner) {
        System.out.print("> ");
        return scanner.next();
    }
}
