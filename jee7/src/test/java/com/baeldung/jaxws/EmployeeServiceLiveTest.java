package com.baeldung.jaxws;

import com.baeldung.jaxws.exception.EmployeeAlreadyExists;
import com.baeldung.jaxws.exception.EmployeeNotFound;
import com.baeldung.jaxws.model.Employee;
import com.baeldung.jaxws.repository.EmployeeRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class EmployeeServiceLiveTest {

    private static final String APP_NAME = "jee7";
    private static final String WSDL_PATH = "EmployeeService?wsdl";
    private static QName SERVICE_NAME = new QName("http://jaxws.baeldung.com/", "EmployeeService");
    private static URL wsdlUrl;

    @ArquillianResource
    private URL deploymentUrl;

    private EmployeeService employeeServiceProxy;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, APP_NAME + ".war").addPackage(EmployeeService.class.getPackage()).addPackage(Employee.class.getPackage()).addPackage(EmployeeNotFound.class.getPackage()).addPackage(EmployeeRepository.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp() {
        try {
            wsdlUrl = new URL(deploymentUrl, WSDL_PATH);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Service service = Service.create(wsdlUrl, SERVICE_NAME);
        employeeServiceProxy = service.getPort(EmployeeService.class);
    }

    @Test
    public void givenGetAllEmployees_thenCorrectNumberOfEmployeesReturned() {
        int employeeCount = employeeServiceProxy.countEmployees();
        List<Employee> employeeList = employeeServiceProxy.getAllEmployees();
        assertEquals(employeeList.size(), employeeCount);
    }

    @Test
    public void givenEmployeeId_whenEmployeeExists_thenCorrectEmployeeReturned() throws EmployeeNotFound {
        Employee employee = employeeServiceProxy.getEmployee(2);
        assertEquals(employee.getFirstName(), "Jack");
    }

    @Test(expected = EmployeeNotFound.class)
    public void givenEmployeeId_whenEmployeeNotExists_thenEmployeeNotFoundExceptionReturned() throws EmployeeNotFound {
        employeeServiceProxy.getEmployee(20);
    }

    @Test
    public void givenAddEmployee_whenEmployeeDoesntAlreadyExist_thenEmployeeCountIncreased() throws EmployeeAlreadyExists {
        int employeeCount = employeeServiceProxy.countEmployees();
        employeeServiceProxy.addEmployee(4, "Anna");
        assertEquals(employeeServiceProxy.countEmployees(), employeeCount + 1);
    }

    @Test(expected = EmployeeAlreadyExists.class)
    public void givenAddEmployee_whenEmployeeAlreadyExist_thenEmployeeAlreadyExistsExceptionReturned() throws EmployeeAlreadyExists {
        employeeServiceProxy.addEmployee(1, "Anna");
    }

    @Test
    public void givenUpdateEmployee_whenEmployeeExists_thenUpdatedEmployeeReturned() throws EmployeeNotFound {
        Employee updated = employeeServiceProxy.updateEmployee(1, "Joan");
        assertEquals(updated.getFirstName(), "Joan");
    }

    @Test(expected = EmployeeNotFound.class)
    public void givenUpdateEmployee_whenEmployeeNotExists_thenUpdatedEmployeeReturned() throws EmployeeNotFound {
        employeeServiceProxy.updateEmployee(20, "Joan");
    }

    @Test
    public void givenDeleteEmployee_whenEmployeeExists_thenCorrectStatusReturned() throws EmployeeNotFound {
        boolean deleteEmployee = employeeServiceProxy.deleteEmployee(3);
        assertEquals(deleteEmployee, true);
    }

    @Test(expected = EmployeeNotFound.class)
    public void givenDeleteEmployee_whenEmployeeNotExists_thenEmployeeNotFoundExceptionReturned() throws EmployeeNotFound {
        employeeServiceProxy.deleteEmployee(20);
    }

}
