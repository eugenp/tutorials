package com.baeldung.hexagonal.main;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonal.adapters.EmployeeConsoleServiceImpl;
import com.baeldung.hexagonal.domain.EmployeeConsoleService;
import com.baeldung.hexagonal.domain.EmployeeService;


public class EmployeeMain {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeMain.class);

	  /**
	   * Program entry point
	   */
	public static void main(String[] args) {
	  
	   EmployeeService service = new EmployeeService();
	    try (final Scanner scanner = new Scanner(System.in)) {
	      boolean exit = false;
	      while (!exit) {
	        printMainMenu();
	        String cmd = readString(scanner);
	       EmployeeConsoleService employeeConsoleService = new EmployeeConsoleServiceImpl(LOGGER);
	        if ("1".equals(cmd)) {
	          employeeConsoleService.createEmployee(service, scanner);
	        } else if ("2".equals(cmd)) {
	          employeeConsoleService.retrieveEmployee(service, scanner);
	        } else if ("3".equals(cmd)) {
	          exit = true;
	        } else {
	          LOGGER.info("Unknown command");
	        }
	      }
	    }
	  }
	

	  private static void printMainMenu() {
	    LOGGER.info("Welcome to Employee Console");
	    LOGGER.info("### Employee Service Console ###");
	    LOGGER.info("1) Create Employee");
	    LOGGER.info("(2) Retrieve Employee");
	    LOGGER.info("(3) Exit");
	  }

	  private static String readString(Scanner scanner) {
	    System.out.print("> ");
	    return scanner.next();
	  }
}

