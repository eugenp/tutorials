package com.baeldung.dddhexagonalapp.cli.adaptor;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CmdLineClient {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new CmdLineModule());
        CmdLineInterface commandConsole = injector.getInstance(CmdLineInterface.class);
        CmdLineClient.start(commandConsole);

    }

    public static void start(CmdLineInterface commandConsole) {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String cmd = CmdLineUtils.readString(scanner);
            if ("ALL".equalsIgnoreCase(cmd)) {
                commandConsole.list(scanner);
            } else if ("REGISTER".equalsIgnoreCase(cmd)) {
                commandConsole.register(scanner);
            } else if ("UPGRADE".equalsIgnoreCase(cmd)) {
                commandConsole.upgrade(scanner);
            } else if ("DOWNGRADE".equalsIgnoreCase(cmd)) {
                commandConsole.downgrade(scanner);
            } else if ("INFO".equalsIgnoreCase(cmd)) {
                commandConsole.info();
            } else if ("EXIT".equalsIgnoreCase(cmd)) {
                exit = true;
            } else {
                commandConsole.info();
            }
        }

    }

}
