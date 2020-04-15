package com.baeldung.dddhexagonalapp.cli.adaptor;

import java.util.Scanner;

public class CmdLineClient {

    public static void main(String[] args) {

        CmdLineConsole commandConsole = new CmdLineConsole();

        CmdLineClient.start(commandConsole);

    }

    public static void start(CmdLineConsole commandConsole) {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String cmd = readString(scanner);
            if ("ALL".equalsIgnoreCase(cmd)) {
                commandConsole.list(scanner);
            } else if ("REGISTER".equalsIgnoreCase(cmd)) {
                commandConsole.register(scanner);
            } else if ("INFO".equalsIgnoreCase(cmd)) {
                commandConsole.info();
            } else if ("EXIT".equalsIgnoreCase(cmd)) {
                exit = true;
            } else {
                commandConsole.info();
            }
        }

    }

    public static String readString(Scanner scanner) {
        System.out.print("$ ");
        return scanner.next();
    }
}
