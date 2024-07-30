package com.baeldung.jaxws.client;

import java.net.URI;
import java.net.URL;
import java.util.List;

public class EmployeeServiceClient {

    public static void main(String[] args) throws Exception {

        URL url = new URI("http://localhost:8080/employeeservice?wsdl").toURL();

        EmployeeService_Service employeeService_Service = new EmployeeService_Service(url);
        EmployeeService employeeServiceProxy = employeeService_Service.getEmployeeServiceImplPort();

        List<Employee> allEmployees = employeeServiceProxy.getAllEmployees();

    }
}