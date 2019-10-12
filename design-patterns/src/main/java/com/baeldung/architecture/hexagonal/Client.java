package com.baeldung.architecture.hexagonal;


import com.baeldung.architecture.hexagonal.dao.EmployeeDao;
import com.baeldung.architecture.hexagonal.dao.EmployeeDaoImpl;
import com.baeldung.architecture.hexagonal.model.Employee;
import com.baeldung.architecture.hexagonal.service.EmployeeService;
import com.baeldung.architecture.hexagonal.service.EmployeeServiceImpl;

import java.util.List;

public class Client {
    public static void main(String[] args) {

        EmployeeDao employeeDao = new EmployeeDaoImpl();
        EmployeeService employeeService = new EmployeeServiceImpl(employeeDao);
        EmployeeControllerAdapter employeeControllerAdapter = new EmployeeControllerAdapter(employeeService);

        Employee employee1 = new Employee();
        employee1.setEmpId("a123");
        employee1.setEmpName("alex");
        Employee employee2 = new Employee();
        employee2.setEmpId("a124");
        employee2.setEmpName("max");
        Employee employee3 = new Employee();
        employee3.setEmpId("a125");
        employee3.setEmpName("susan");

        employeeControllerAdapter.createEmployee(employee1);
        employeeControllerAdapter.createEmployee(employee2);
        employeeControllerAdapter.createEmployee(employee3);

        List<Employee> employees = employeeControllerAdapter.getEmployeeList();
        for(Employee employee : employees){
            System.out.println("Employee ID: "+employee.getEmpId()+", Employee Name: "+employee.getEmpName());
        }
    }
}
