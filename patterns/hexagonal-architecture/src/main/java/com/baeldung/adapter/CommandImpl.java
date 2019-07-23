package com.baeldung.adapter;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.baeldung.appcore.domain.Customer;
import com.baeldung.appcore.service.CustomerService;
import com.baeldung.tests.ConsoleUtil;

public class CommandImpl implements Command {
    private CustomerService customerService;

    public CommandImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void getAllCustomers(Scanner scanner) {
        System.out.println("All customers registered so far");

        List<Customer> customers = customerService.getAllCustomers();

        customers.forEach(customer -> {
                    ConsoleUtil.logCustomerToConsole(customer);
                    System.out.println("------------------------------------");
                });

    }

    @Override
    public void registerCustomer(Scanner scanner) {
        System.out.println("What is the first name?");
        String firstName = ConsoleUtil.readString(scanner);

        System.out.println("What is the last name?");
        String lastName = ConsoleUtil.readString(scanner);

        Customer unregistered = new Customer();
        unregistered.setFirstName(firstName);
        unregistered.setLastName(lastName);
        Customer registered = customerService.registerCustomer(unregistered);

        System.out.println("Customer registered successfully with the following: ");
        ConsoleUtil.logCustomerToConsole(registered);

    }

    @Override
    public void updateCustomer(Scanner scanner) {
        System.out.println("What is the customer Id you want to update?");
        String customerId = ConsoleUtil.readString(scanner);
        Optional<Customer> customerWrapper = customerService.findCustomerById(Integer.valueOf(customerId));

        if (customerWrapper.isPresent()) {
            System.out.println("The Customer you want to upgrade is as following");
            ConsoleUtil.logCustomerToConsole(customerWrapper.get());
            System.out.println("Do you want to proceed ? Y(es)/N(o)");
            String confirm = ConsoleUtil.readString(scanner);

            if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {

                    System.out.println("What is the first name?");
                    String firstName = ConsoleUtil.readString(scanner);

                    System.out.println("What is the last name?");
                    String lastName = ConsoleUtil.readString(scanner);

                    Customer updatedCustomer = new Customer();
                    updatedCustomer.setFirstName(firstName);
                    updatedCustomer.setLastName(lastName);

                    Customer customer = customerService.updateCustomer(updatedCustomer);
                    System.out.println("Customer upgrade successful with the following : ");
                    ConsoleUtil.logCustomerToConsole(customer);
            }
        } else {
            System.out.println("Can't find the customer with supplied Id. Please try again ");
        }
    }

    @Override
    public void info() {
        ConsoleUtil.printMainMenu();
    }
}