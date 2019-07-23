package com.baeldung.adapter;

import java.util.Scanner;

public interface Command {
    void getAllCustomers(Scanner scanner);

    void registerCustomer(Scanner scanner);

    void updateCustomer(Scanner scanner);

    void info();
}