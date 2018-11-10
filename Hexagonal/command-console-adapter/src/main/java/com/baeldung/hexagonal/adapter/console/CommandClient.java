package com.baeldung.hexagonal.adapter.console;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CommandClient {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new CommandConsoleModule());
        CommandInterface commandConsole = injector.getInstance(CommandInterface.class);
        CommandClient.start(commandConsole);

    }

    public static void start(CommandInterface commandConsole) {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String cmd = CommandConsoleUtils.readString(scanner);
            if ("LS".equalsIgnoreCase(cmd)) {
                commandConsole.list(scanner);
            } else if ("REG".equalsIgnoreCase(cmd)) {
                commandConsole.register(scanner);
            } else if ("UG".equals(cmd)) {
                commandConsole.upgrade(scanner);
            } else if ("DG".equals(cmd)) {
                commandConsole.downgrade(scanner);
            } else if ("INFO".equals(cmd)) {
                commandConsole.info();
            } else if ("EXIT".equalsIgnoreCase(cmd)) {
                exit = true;
            } else {
                commandConsole.info();
            }
        }

    }

}
