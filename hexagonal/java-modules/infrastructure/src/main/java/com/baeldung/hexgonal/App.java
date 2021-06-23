package com.baeldung.hexgonal;



import com.baeldung.hexgonal.repo.EmployeeRepositoryImpl;
import com.baeldung.hexgonal.services.EmployeeService;
import com.baeldung.hexgonal.services.EmployeeServiceFactory;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        EmployeeService employeeService = EmployeeServiceFactory.getInstance(new EmployeeRepositoryImpl());
        ApplicationCLI applicationCLI = new ApplicationCLI(new Scanner(System.in), employeeService);
        applicationCLI.commandHandler();
    }

}
