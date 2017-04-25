package com.baeldung.jaxws.client;


import com.baeldung.jaxws.server.bottomup.EmployeeService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class EmployeeServiceClient {

    public static void main(String[] args) throws Exception {

        URL url = new URL("http://localhost:8081/employeeservice?wsdl");
        QName qname = new QName("http://bottomup.server.jaxws.baeldung.com/", "EmployeeServiceImplService");

        Service service = Service.create(url, qname);

        EmployeeService employeeService = service.getPort(EmployeeService.class);
        employeeService.countEmployees();

        System.out.println(employeeService.countEmployees());

    }
}