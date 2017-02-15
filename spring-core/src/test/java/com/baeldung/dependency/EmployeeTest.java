package com.baeldung.dependency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class EmployeeTest {

    @Autowired
    Employee employee;

    @Before
    public void setup() {
        employee.getPayDetails()
            .setBasicPay(15000);

    }

    @Test
    public void whenContextLoadsDepartmentDetailsInjected() {
        assertNotNull("Employee Department is not loaded correctly", employee.getDepartmentDetails());
    }

    @Test
    public void whenContextLoadsAddressDetailsInjected() {
        assertNotNull("Employee Address is not loaded correctly", employee.getAddressDetails());
    }

    @Test
    public void whenContextLoadsPayDetailInjected() {
        assertNotNull("Employee Pay detail is not loaded correctly", employee.getPayDetails());
    }

    @Test
    public void whenPayDetailLoadsThenReturnsBasicPay() {
        assertEquals("Employee Basic Pay not returned correctly", 15000, employee.getPayDetails()
            .getBasicPay());
    }
}
