/**
 * 
 */
package com.baeldung.hexagonal;

import java.util.Scanner;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.domain.Input;

public class ConsoleInput implements Input {

    public Employee createEmployee() {
        Employee employee = null;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter Employee first Name:");
            String firstName = scanner.next();
            System.out.println("Enter Employee last Name:");
            String lastName = scanner.next();
            employee = new Employee(lastName, firstName);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	scanner.close();
        }
        return employee;
    }

}
