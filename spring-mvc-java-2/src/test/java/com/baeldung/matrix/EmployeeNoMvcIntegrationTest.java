package com.baeldung.matrix;

import com.baeldung.matrix.config.MatrixWebConfig;
import com.baeldung.matrix.controller.EmployeeController;
import com.baeldung.matrix.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MatrixWebConfig.class, EmployeeController.class })
public class EmployeeNoMvcIntegrationTest {

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setup() {
        employeeController.initEmployees();
    }

    //

    @Test
    public void whenInitEmployees_thenVerifyValuesInitiation() {
        Employee employee1 = employeeController.employeeMap.get(1L);
        Employee employee2 = employeeController.employeeMap.get(2L);
        Employee employee3 = employeeController.employeeMap.get(3L);

        Assert.assertTrue(employee1.getId() == 1L);
        Assert.assertTrue(employee1.getName().equals("John"));
        Assert.assertTrue(employee1.getContactNumber().equals("223334411"));
        Assert.assertTrue(employee1.getWorkingArea().equals("rh"));

        Assert.assertTrue(employee2.getId() == 2L);
        Assert.assertTrue(employee2.getName().equals("Peter"));
        Assert.assertTrue(employee2.getContactNumber().equals("22001543"));
        Assert.assertTrue(employee2.getWorkingArea().equals("informatics"));

        Assert.assertTrue(employee3.getId() == 3L);
        Assert.assertTrue(employee3.getName().equals("Mike"));
        Assert.assertTrue(employee3.getContactNumber().equals("223334411"));
        Assert.assertTrue(employee3.getWorkingArea().equals("admin"));
    }

}
