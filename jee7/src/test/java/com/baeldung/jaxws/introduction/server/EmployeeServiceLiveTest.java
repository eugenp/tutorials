package com.baeldung.jaxws.introduction.server;

import com.baeldung.jaxws.introduction.server.model.Employee;
import org.junit.Before;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class EmployeeServiceLiveTest {


    private static QName SERVICE_NAME = new QName("http://server.introduction.jaxws.baeldung.com/", "EmployeeServiceImplService");

    private URL wsdlUrl;

    private Service service;
    private EmployeeService employeeServiceProxy;

    {
        try {
            wsdlUrl = new URL("http://localhost:8080/employeeservice?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        service = Service.create(wsdlUrl, SERVICE_NAME);
    }


    @Before
    public void setUp() {
        employeeServiceProxy = service.getPort(EmployeeService.class);
    }

    @Test
    public void givenGetAllEmployees_thenCorrectNumberOfEmployeesReturned() {

        List<Employee> employeeList = employeeServiceProxy.getAllEmployees();
        assertEquals(employeeList.size(), 3);
    }

    @Test
    public void givenGetEmployee_whenEmployeeExist_thenCorrectEmployeeReturned() {

        Employee employee = employeeServiceProxy.getEmployee(2);
        assertEquals(employee.getFirstName(), "Jack");
    }
}
