package com.baeldung.beaninjection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { OrganizationConfiguration.class })
public class OrganizationConfigurationUnitTest {

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testDepartmentServiceCreation() {
        Assert.assertNotNull(departmentService);
        Assert.assertNotNull(departmentService.getEmployee());
        Assert.assertNotNull(departmentService.getAccountsDept());
        Assert.assertNotNull(departmentService.getComputerDept());
    }

}
