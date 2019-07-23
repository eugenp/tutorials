package com.baeldung.tests;

import java.util.Scanner;

import com.baeldung.adapter.Command;
import com.baeldung.adapter.CommandImpl;
import com.baeldung.appcore.repository.CustomerRepository;
import com.baeldung.appcore.service.CustomerService;
import com.baeldung.appcore.service.CustomerServiceImpl;
import com.baeldung.infrastructure.InMemoryCustomerRepositoryImpl;

public class Client {
    public static void main(String[] args) {
        CustomerRepository inMemoryCustomerRepository = new InMemoryCustomerRepositoryImpl();

        CustomerService customerService = new CustomerServiceImpl(inMemoryCustomerRepository);
        Command commandConsole = new CommandImpl(customerService);
        Client.start(commandConsole);
    }

    public static void start(Command commandConsole) {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        commandConsole.info();
        while (!exit) {
            String cmd = ConsoleUtil.readString(scanner);
            if ("LS".equalsIgnoreCase(cmd)) {
                commandConsole.getAllCustomers(scanner);
            } else if ("REG".equalsIgnoreCase(cmd)) {
                commandConsole.registerCustomer(scanner);
            } else if ("UPD".equals(cmd)) {
                commandConsole.updateCustomer(scanner);
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