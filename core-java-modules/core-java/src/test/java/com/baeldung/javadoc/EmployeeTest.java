package com.baeldung.javadoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    public void testShallowCopy(){

        Department department = new Department("Finance");
        Employee seniorDeveloper = new Employee(department);
        Employee juniorDeveloper = seniorDeveloper.shallowClone();

        Department juniorDeveloperDepartment = juniorDeveloper.getDepartment();
        juniorDeveloperDepartment.setName("OrderManagement");

        assertEquals(juniorDeveloper.getDepartment().getName(),"OrderManagement");
        assertEquals(seniorDeveloper.getDepartment().getName(),"OrderManagement");

    }

    @Test
    public void testDeepCopy(){

        Department department = new Department("Finance");
        Employee seniorDeveloper = new Employee(department);
        Employee juniorDeveloper = seniorDeveloper.deepClone();

        Department juniorDeveloperDepartment = juniorDeveloper.getDepartment();
        juniorDeveloperDepartment.setName("OrderManagement");

        assertEquals(juniorDeveloper.getDepartment().getName(),"OrderManagement");
        assertEquals(seniorDeveloper.getDepartment().getName(),"Finance");

    }

}
