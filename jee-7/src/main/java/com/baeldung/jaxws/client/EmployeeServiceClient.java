package com.baeldung.jaxws.client;

import java.net.URL;
import java.util.List;

public class EmployeeServiceClient {

    public static void main(String[] args) throws Exception {

        URL url = new URL("http://localhost:8080/employeeservice?wsdl");

        EmployeeService_Service employeeService_Service = new EmployeeService_Service(url);
        EmployeeService employeeServiceProxy = employeeService_Service.getEmployeeServiceImplPort();

        List<Employee> allEmployees = employeeServiceProxy.getAllEmployees();

    }
}