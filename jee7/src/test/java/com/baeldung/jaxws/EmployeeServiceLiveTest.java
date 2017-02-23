package com.baeldung.jaxws;


import com.baeldung.jaxws.model.Employee;
import com.baeldung.jaxws.model.Result;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class EmployeeServiceLiveTest {


    private static QName SERVICE_NAME = new QName("http://jaxws.baeldung.com/", "EmployeeServiceImplService");

    private static URL wsdlUrl;

    private static Service service;
    private EmployeeService employeeServiceProxy;
    private int employeeCount = 0;
    private static Endpoint endpoint;


    @BeforeClass
    public static void beforeClass() {
        endpoint = Endpoint.publish("http://localhost:8080/employeeservice", new EmployeeServiceImpl());

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
        employeeCount = (int) employeeServiceProxy.countEmployees().getResponse();
    }

    @AfterClass
    public static void afterClass() {
        endpoint.stop();
    }


    @Test
    public void givenGetAllEmployees_thenCorrectNumberOfEmployeesReturned() {
        List<Employee> employeeList = employeeServiceProxy.getAllEmployees();
        assertEquals(employeeList.size(), employeeCount);
    }

    @Test
    public void givenGetEmployee_whenEmployeeExist_thenCorrectEmployeeReturned() {

        Result result = employeeServiceProxy.getEmployee(2);
        Employee employee = (Employee) result.getResponse();
        assertEquals(employee.getFirstName(), "Jack");
    }

    @Test
    public void givenAddEmployee_whenEmployeeDoesntAlreadyExist_thenEmployeeCountIncreased() {

        Employee employee = new Employee(4, "Anna");
        employeeServiceProxy.addEmployee(employee);
        assertEquals(employeeServiceProxy.countEmployees().getResponse(), employeeCount + 1);
    }

    @Test
    public void givenUpdateEmployee_whenEmployeeExists_thenUpdatedEmployeeReturned() {

        Employee employee = new Employee(1, "Joan");
        Result result = employeeServiceProxy.updateEmployee(employee, 1);
        Employee updated = (Employee) result.getResponse();
        assertEquals(updated.getFirstName(), "Joan");
    }

    @Test
    public void givenDeleteEmployee_whenEmployeeExists_thenEmployeeCountDecreased() {

        employeeServiceProxy.deleteEmployee(3);
        assertEquals(employeeServiceProxy.countEmployees().getResponse(), employeeCount - 1);
    }

    @Test
    public void givenGetEmployee_whenEmployeeDoesntExist_thenCorrectErrorThrown() {

        Result result = employeeServiceProxy.getEmployee(7);
        assertEquals(result.getErrorMessage(), "The specified employee does not exist");
    }

    @Test
    public void givenAddEmployee_whenEmployeeAlreadyExist_thenCorrectErrorThrown() {
        Employee employee = new Employee(2, "Anna");
        Result result = employeeServiceProxy.addEmployee(employee);
        assertEquals(result.getErrorMessage(), "This employee already exist");
    }

    @Test
    public void givenUpdateEmployee_whenEmployeeDoesntExist_thenCorrectErrorThrown() {

        Employee employee = new Employee(6, "John");
        Result result = employeeServiceProxy.updateEmployee(employee, 6);
        assertEquals(result.getErrorMessage(), "The specified employee does not exist");
    }

    @Test
    public void givenDeleteEmployee_whenEmployeeDoesntExist_thenCorrectErrorThrown() {
        Result result = employeeServiceProxy.deleteEmployee(8);
        assertEquals(result.getErrorMessage(), "The specified employee does not exist");
    }

}
