package com.baeldung.adapters;

import java.util.Scanner;

import com.baeldung.ports.UserInterfaceRequestPort;

public class UserConsoleInterfaceAdapter {
    private final UserInterfaceRequestPort requestPort;

    public UserConsoleInterfaceAdapter(UserInterfaceRequestPort requestPort) {
        this.requestPort = requestPort;
    }

    public void buyACar() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose car to buy");
        System.out.println("enter manufacturer:");
        String manufacturer = scanner.nextLine();

        System.out.println("enter model:");
        String model = scanner.nextLine();

        requestPort.buy(manufacturer, model);
    }
}
