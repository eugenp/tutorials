package com.baeldung.resttemplate.lists.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.baeldung.resttemplate.lists.dto.Employee;
import com.baeldung.resttemplate.lists.dto.EmployeeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Application that shows how to use Lists with RestTemplate.
 */
public class EmployeeClient
{
    public static void main(String[] args)
    {
        EmployeeClient employeeClient = new EmployeeClient();

        System.out.println("Calling GET using ParameterizedTypeReference");
        employeeClient.getAllEmployeesUsingParameterizedTypeReference();

        System.out.println("Calling GET using wrapper class");
        employeeClient.getAllEmployeesUsingWrapperClass();

        System.out.println("Calling POST using normal lists");
        employeeClient.createEmployeesUsingLists();

        System.out.println("Calling POST using wrapper class");
        employeeClient.createEmployeesUsingWrapperClass();
    }

    public EmployeeClient()
    {

    }

    public List<Employee> getAllEmployeesUsingParameterizedTypeReference()
    {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Employee>> response =
                restTemplate.exchange(
                        "http://localhost:8082/spring-rest/employees/",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Employee>>(){});

        List<Employee> employees = response.getBody();

        employees.forEach(e -> System.out.println(e));

        return employees;
    }

    public List<Employee> getAllEmployeesUsingWrapperClass()
    {
        RestTemplate restTemplate = new RestTemplate();

        EmployeeList response =
                restTemplate.getForObject(
                        "http://localhost:8082/spring-rest/employees/v2",
                        EmployeeList.class);

        List<Employee> employees = response.getEmployees();

        employees.forEach(e -> System.out.println(e));

        return employees;
    }

    public void createEmployeesUsingLists()
    {
        RestTemplate restTemplate = new RestTemplate();

        List<Employee> newEmployees = new ArrayList<>();
        newEmployees.add(new Employee(3, "Intern"));
        newEmployees.add(new Employee(4, "CEO"));

        restTemplate.postForObject(
                "http://localhost:8082/spring-rest/employees/",
                newEmployees,
                ResponseEntity.class);
    }

    public void createEmployeesUsingWrapperClass()
    {
        RestTemplate restTemplate = new RestTemplate();

        List<Employee> newEmployees = new ArrayList<>();
        newEmployees.add(new Employee(3, "Intern"));
        newEmployees.add(new Employee(4, "CEO"));

        restTemplate.postForObject(
                "http://localhost:8082/spring-rest/employees/v2/",
                new EmployeeList(newEmployees),
                ResponseEntity.class);
    }
}
