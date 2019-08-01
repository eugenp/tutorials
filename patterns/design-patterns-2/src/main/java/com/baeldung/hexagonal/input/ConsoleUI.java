package com.baeldung.hexagonal.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.baeldung.hexagonal.central.service.IAccountService;
import com.baeldung.hexagonal.central.ui.IAccountUI;

public class ConsoleUI implements IAccountUI {

    private IAccountService accountService;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    public void printWelcomeMessage() {

        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to Account Application!");
        sb.append("\n");
        sb.append("Usage:");
        sb.append("* new account:");
        sb.append("\tcreate <user-name> <role>");

        System.out.println(sb.toString());
    }

    public Map<String, String> readParameters() {

        Scanner scanner = new Scanner(System.in);

        Map<String, String> params = new HashMap<>();

        String name = scanner.next();
        String type = scanner.next();

        params.put("name", name);
        params.put("type", type);

        return params;
    }

    @Override
    public void create(Map<String, String> parameters) {

        String name = parameters.get("name");
        String type = parameters.get("type");

        accountService.createAccount(name, type);
    }

}
