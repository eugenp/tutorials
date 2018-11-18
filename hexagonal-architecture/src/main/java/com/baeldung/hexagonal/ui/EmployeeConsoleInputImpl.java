package com.baeldung.hexagonal.ui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.domain.EmployeeService;

public class EmployeeConsoleInputImpl implements EmployeeInput {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeConsoleInputImpl.class);

    public void enterEmployee(EmployeeService service, Scanner scanner) {
        LOG.info("ID: ");
        System.out.print("> ");
        Long id = scanner.nextLong();
        LOG.info("Name: ");
        System.out.print("> ");
        String name = scanner.next();

        Employee employee = new Employee(id, name);
        service.add(employee);
    }

    @Override
    public void collectData(EmployeeService service) {
        try (final Scanner scanner = new Scanner(System.in)) {
            String keepGoing = "Y";
            do {
                LOG.info("Enter information for an employee");
                enterEmployee(service, scanner);
                LOG.info("Do you want to enter another employee? (Y/N)");
                System.out.print("> ");
                keepGoing = scanner.next();
                if (keepGoing.length() > 1) {
                    keepGoing = keepGoing.substring(0, 1);
                }
            } while (keepGoing.equalsIgnoreCase("Y"));
        }
    }

}
