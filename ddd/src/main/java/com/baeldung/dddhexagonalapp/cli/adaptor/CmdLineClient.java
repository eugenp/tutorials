package com.baeldung.dddhexagonalapp.cli.adaptor;

import java.util.Scanner;

import com.baeldung.dddhexagonalapp.coreapp.service.CardHolderServiceImpl;
import com.baeldung.dddhexagonalapp.repository.adaptor.CardHolderRepositoryImpl;

public class CmdLineClient {

    public static void main(String[] args) {

        CmdLineInterfaceImpl commandConsole = new CmdLineInterfaceImpl(new CardHolderServiceImpl(new CardHolderRepositoryImpl() {
        }));

        CmdLineClient.start(commandConsole);

    }

    public static void start(CmdLineInterfaceImpl commandConsole) {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String cmd = CmdLineUtils.readString(scanner);
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

}
