package com.baeldung.jaxws;

import com.baeldung.jaxws.model.Employee;
import com.baeldung.jaxws.repository.EmployeeRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceLiveTest {

    private static QName SERVICE_NAME = new QName("http://jaxws.baeldung.com/", "EmployeeServiceImplService");

    private static URL wsdlUrl;

    private static Service service;
    private static Endpoint endpoint;
    private EmployeeService employeeServiceProxy;
    private int employeeCount = 0;

    @InjectMocks private static EmployeeService employeeServiceImpl = new EmployeeServiceImpl();

    @Mock private EmployeeRepository employeeRepositoryImpl;

    {
        employeeServiceProxy = service.getPort(EmployeeService.class);

    }

    @BeforeClass
    public static void beforeClass() {
        endpoint = Endpoint.publish("http://localhost:8080/employeeservice", employeeServiceImpl);
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
        doReturn(new Employee(2, "Jack"))
          .when(employeeRepositoryImpl)
          .getEmployee(2);
        Employee employee = employeeServiceProxy.getEmployee(2);
        assertEquals(employee.getFirstName(), "Jack");
    }

    @Test
    public void givenAddEmployee_whenEmployeeDoesntAlreadyExist_thenEmployeeCountIncreased() {
        doReturn(employeeCount + 1)
          .when(employeeRepositoryImpl)
          .count();
        Employee employee = employeeServiceProxy.addEmployee(4, "Anna");
        assertEquals(employeeServiceProxy.countEmployees(), employeeCount + 1);
    }

    @Test
    public void givenUpdateEmployee_whenEmployeeExists_thenUpdatedEmployeeReturned() {
        Employee employee = new Employee(1, "Joan");
        doReturn(employee)
          .when(employeeRepositoryImpl)
          .updateEmployee(1, "Joan");
        Employee updated = employeeServiceProxy.updateEmployee(1, "Joan");
        assertEquals(updated.getFirstName(), "Joan");
    }

    @Test
    public void givenDeleteEmployee_whenEmployeeExists_thenCorrectStatusReturned() {
        when(employeeRepositoryImpl.deleteEmployee(3)).thenReturn(true);
        boolean deleteEmployee = employeeServiceProxy.deleteEmployee(3);
        assertEquals(deleteEmployee, true);
    }

}
