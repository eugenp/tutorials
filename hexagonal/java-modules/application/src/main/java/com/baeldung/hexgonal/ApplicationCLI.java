package com.baeldung.hexgonal;

import com.baeldung.hexgonal.services.EmployeeService;

import java.util.Scanner;

public class ApplicationCLI {

    private final Scanner scanner;
    private final EmployeeService employeeService;

    public ApplicationCLI(Scanner scanner, EmployeeService employeeService) {
        this.scanner = scanner;
        this.employeeService = employeeService;
    }

    public void commandHandler() {
        do {
            System.out.println("Enter C to create a employee, D to delete a employee or E to exit the application: ");
            final String command = scanner.next();
            switch (command) {
                case "C":
                    createEmployee();
                    break;
                case "D":
                    deleteEmployee();
                    break;
                case "E":
                    return;
            }
        } while (true);
    }

    private void createEmployee() {
        System.out.println("Enter employee id: ");
        String id = scanner.next();
        System.out.println("Enter employee first name: ");
        String firstName = scanner.next();
        System.out.println("Enter employee last name: ");
        String lastName = scanner.next();
        System.out.println("Enter employee code : ");
        String code = scanner.next();
        employeeService.createEmployee(Long.valueOf(id), firstName, lastName, code);
        System.out.println("Book " + id + " successfully created.");
    }

    private void deleteEmployee() {
        System.out.println("Enter ID: ");
        final String id = scanner.next();
        employeeService.deleteEmployee(Long.valueOf(id));
        System.out.println("Employee " + id + " successfully deleted.");
    }

}
