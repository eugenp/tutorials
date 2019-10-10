package com.baeldung.hexagonal;

import com.baeldung.hexagonal.model.Employee;
import com.baeldung.hexagonal.service.EmployeeService;
import com.baeldung.hexagonal.service.EmployeeServiceImpl;

import java.util.List;

public class Client {
    public static void main(String[] args) {
        OutboundPort secondaryAdapter = new SecondaryAdapter();
        EmployeeService employeeService = new EmployeeServiceImpl(secondaryAdapter);
        InboundPort primaryAdapter = new PrimaryAdapter(employeeService);

        Employee employee1 = new Employee();
        employee1.setEmpId("a123");
        employee1.setEmpName("alex");
        Employee employee2 = new Employee();
        employee2.setEmpId("a124");
        employee2.setEmpName("max");
        Employee employee3 = new Employee();
        employee3.setEmpId("a125");
        employee3.setEmpName("susan");

        primaryAdapter.createEmployee(employee1);
        primaryAdapter.createEmployee(employee2);
        primaryAdapter.createEmployee(employee3);

        List<Employee> employees = primaryAdapter.getEmployeeList();
        for(Employee employee : employees){
            System.out.println("Employee ID: "+employee.getEmpId()+", Employee Name: "+employee.getEmpName());
        }
    }
}
