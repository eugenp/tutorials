package com.baeldung.spring.jdbc.template.guide;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.spring.jdbc.template.guide.Employee;
import com.baeldung.spring.jdbc.template.guide.EmployeeDAO;
import com.baeldung.spring.jdbc.template.guide.config.SpringJdbcConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringJdbcConfig.class }, loader = AnnotationConfigContextLoader.class)
public class EmployeeDAOIntegrationTest {

    @Autowired
    private EmployeeDAO employeeDao;

    @Test
    public void testGetCountOfEmployees() {
        Assert.assertEquals(employeeDao.getCountOfEmployees(), 9);
    }

    @Test
    public void testQueryMethod() {
        Assert.assertEquals(employeeDao.getAllEmployees().size(), 4);
    }

    @Test
    public void testUpdateMethod() {
        Assert.assertEquals(employeeDao.addEmplyee(5), 1);
    }

    @Test
    public void testAddEmployeeUsingSimpelJdbcInsert() {
        final Employee emp = new Employee();
        emp.setId(11);
        emp.setFirstName("testFirstName");
        emp.setLastName("testLastName");
        emp.setAddress("testAddress");

        Assert.assertEquals(employeeDao.addEmplyeeUsingSimpelJdbcInsert(emp), 1);
    }

    @Test
    public void testExecuteMethod() {
        employeeDao.addEmplyeeUsingExecuteMethod();
    }

    @Test
    public void testMapSqlParameterSource() {
        Assert.assertEquals("James", employeeDao.getEmployeeUsingMapSqlParameterSource());
    }

    @Test
    public void testBeanPropertySqlParameterSource() {
        Assert.assertEquals(1, employeeDao.getEmployeeUsingBeanPropertySqlParameterSource());
    }

    @Test
    public void testCustomExceptionTranslator() {
        employeeDao.addEmplyee(7);

        try {
            employeeDao.addEmplyee(7);
        } catch (final DuplicateKeyException e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e.getMessage().contains("Custome Exception translator - Integrity contraint voilation."));
        }
    }

    @Test
    public void testBatchUpdateUsingJDBCTemplate() {
        final List<Employee> employees = new ArrayList<Employee>();
        final Employee emp1 = new Employee();
        emp1.setId(10);
        emp1.setFirstName("firstName1");
        emp1.setLastName("lastName1");
        emp1.setAddress("address1");

        final Employee emp2 = new Employee();
        emp2.setId(20);
        emp2.setFirstName("firstName2");
        emp2.setLastName("lastName2");
        emp2.setAddress("address2");

        final Employee emp3 = new Employee();
        emp3.setId(30);
        emp3.setFirstName("firstName3");
        emp3.setLastName("lastName3");
        emp3.setAddress("address3");

        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);

        employeeDao.batchUpdateUsingJDBCTemplate(employees);

        Assert.assertTrue(employeeDao.getEmployee(10) != null);
        Assert.assertTrue(employeeDao.getEmployee(20) != null);
        Assert.assertTrue(employeeDao.getEmployee(30) != null);
    }

    @Test
    public void testBatchUpdateUsingNamedParameterJDBCTemplate() {
        final List<Employee> employees = new ArrayList<Employee>();
        final Employee emp1 = new Employee();
        emp1.setId(40);
        emp1.setFirstName("firstName4");
        emp1.setLastName("lastName4");
        emp1.setAddress("address4");

        final Employee emp2 = new Employee();
        emp2.setId(50);
        emp2.setFirstName("firstName5");
        emp2.setLastName("lastName5");
        emp2.setAddress("address5");

        final Employee emp3 = new Employee();
        emp3.setId(60);
        emp3.setFirstName("firstName6");
        emp3.setLastName("lastName6");
        emp3.setAddress("address6");

        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);

        employeeDao.batchUpdateUsingNamedParameterJDBCTemplate(employees);

        Assert.assertTrue(employeeDao.getEmployee(40) != null);
        Assert.assertTrue(employeeDao.getEmployee(50) != null);
        Assert.assertTrue(employeeDao.getEmployee(60) != null);
    }
}
