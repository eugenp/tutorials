package com.baeldung.hexagonal;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.domain.EmployeeService;
import com.baeldung.hexagonal.domain.Input;

public class HexagonalExample {

    public static void main(String[] args) {
        Input consoleInput = new ConsoleInput();
        InMemoryEmployeeRepository memoryEmployeeRepository = new InMemoryEmployeeRepository();
        EmployeeService service = new EmployeeService(consoleInput, memoryEmployeeRepository);
        Employee employee = service.registerEmployee();
        System.out.println(employee.toString());
    }

}
