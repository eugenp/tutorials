package com.baeldung.hexagonal.domain;

import java.util.Scanner;



public interface EmployeeConsoleService {
	 /**
	  * Retrieve Employee
	  */
	 void retrieveEmployee(EmployeeService service, Scanner scanner);

	  /**
	  * Create Employee
	  */
	  void createEmployee(EmployeeService service, Scanner scanner);
}
