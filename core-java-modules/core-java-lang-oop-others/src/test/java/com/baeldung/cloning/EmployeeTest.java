package com.baeldung.cloning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

    @Test
    void whenShallowCopy_thenSuccessful() {
        Department dept = new Department(1, "Sales");
        Employee originalObj = new Employee(1, dept);
        Employee copyObj = new Employee(originalObj.getId(), originalObj.getDepartment());
        Assertions.assertNotNull(copyObj);
        copyObj.setId(2);
        copyObj.getDepartment()
            .setDeptId(2);
        copyObj.getDepartment()
            .setDeptName("Marketing");
        Assertions.assertTrue(originalObj.getId() != copyObj.getId());
        Assertions.assertSame(originalObj.getDepartment(), copyObj.getDepartment());
        Assertions.assertEquals(copyObj.getDepartment()
            .getDeptId(), originalObj.getDepartment()
            .getDeptId());
        Assertions.assertEquals(copyObj.getDepartment()
            .getDeptName(), originalObj.getDepartment()
            .getDeptName());

    }
}
