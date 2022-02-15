package com.baeldung.hexagonaljava.adapters.driveradapters;

import java.util.Scanner;

import com.baeldung.hexagonaljava.ports.driverports.EmployeeService;

/**
 * @author Hesam Ghiasi created on 2/15/22 
 */
public class TerminalAdapter {

    private EmployeeService employeeService;

    public TerminalAdapter(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public String addEmployee() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name of the employee: ");
        String name = sc.nextLine();
        System.out.println("Enter year of birth of the employee: ");
        int yearOfBirth = sc.nextInt();
        return employeeService.addEmployee(name, yearOfBirth);
    }
}
