package com.baeldung.jaxws;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.baeldung.jaxws.client.Employee;
import com.baeldung.jaxws.client.EmployeeAlreadyExists_Exception;
import com.baeldung.jaxws.client.EmployeeNotFound_Exception;
import com.baeldung.jaxws.client.EmployeeService;
import com.baeldung.jaxws.client.EmployeeService_Service;

@RunWith(Arquillian.class)
public class EmployeeServiceLiveTest {

    private static final String APP_NAME = "jee7";
    private static final String WSDL_PATH = "EmployeeService?wsdl";
    private static QName SERVICE_NAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "EmployeeService");
    private static URL wsdlUrl;

    @ArquillianResource
    private URL deploymentUrl;

    private EmployeeService employeeServiceProxy;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, APP_NAME + ".war")
            .addPackage(com.baeldung.jaxws.server.bottomup.EmployeeService.class.getPackage())
            .addPackage(com.baeldung.jaxws.server.bottomup.model.Employee.class.getPackage())
            .addPackage(com.baeldung.jaxws.server.bottomup.exception.EmployeeNotFound.class.getPackage())
            .addPackage(com.baeldung.jaxws.server.repository.EmployeeRepository.class.getPackage())
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp() {
        try {
            wsdlUrl = new URL(deploymentUrl, WSDL_PATH);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        EmployeeService_Service employeeService_Service = new EmployeeService_Service(wsdlUrl);
        employeeServiceProxy = employeeService_Service.getEmployeeServiceImplPort();
    }

    @Test
    public void givenEmployees_whenGetCount_thenCorrectNumberOfEmployeesReturned() {
        int employeeCount = employeeServiceProxy.countEmployees();
        List<Employee> employeeList = employeeServiceProxy.getAllEmployees();
        assertEquals(employeeList.size(), employeeCount);
    }

    @Test
    public void givenEmployees_whenGetAvailableEmployee_thenCorrectEmployeeReturned() throws EmployeeNotFound_Exception {
        Employee employee = employeeServiceProxy.getEmployee(2);
        assertEquals(employee.getFirstName(), "Jack");
    }

    @Test(expected = EmployeeNotFound_Exception.class)
    public void givenEmployees_whenGetNonAvailableEmployee_thenEmployeeNotFoundException() throws EmployeeNotFound_Exception {
        employeeServiceProxy.getEmployee(20);
    }

    @Test
    public void givenEmployees_whenAddNewEmployee_thenEmployeeCountIncreased() throws EmployeeAlreadyExists_Exception {
        int employeeCount = employeeServiceProxy.countEmployees();
        employeeServiceProxy.addEmployee(4, "Anna");
        assertEquals(employeeServiceProxy.countEmployees(), employeeCount + 1);
    }

    @Test(expected = EmployeeAlreadyExists_Exception.class)
    public void givenEmployees_whenAddAlreadyExistingEmployee_thenEmployeeAlreadyExistsException() throws EmployeeAlreadyExists_Exception {
        employeeServiceProxy.addEmployee(1, "Anna");
    }

    @Test
    public void givenEmployees_whenUpdateExistingEmployee_thenUpdatedEmployeeReturned() throws EmployeeNotFound_Exception {
        Employee updated = employeeServiceProxy.updateEmployee(1, "Joan");
        assertEquals(updated.getFirstName(), "Joan");
    }

    @Test(expected = EmployeeNotFound_Exception.class)
    public void givenEmployees_whenUpdateNonExistingEmployee_thenEmployeeNotFoundException() throws EmployeeNotFound_Exception {
        employeeServiceProxy.updateEmployee(20, "Joan");
    }

    @Test
    public void givenEmployees_whenDeleteExistingEmployee_thenSuccessReturned() throws EmployeeNotFound_Exception {
        boolean deleteEmployee = employeeServiceProxy.deleteEmployee(3);
        assertEquals(deleteEmployee, true);
    }

    @Test(expected = EmployeeNotFound_Exception.class)
    public void givenEmployee_whenDeleteNonExistingEmployee_thenEmployeeNotFoundException() throws EmployeeNotFound_Exception {
        employeeServiceProxy.deleteEmployee(20);
    }

}
