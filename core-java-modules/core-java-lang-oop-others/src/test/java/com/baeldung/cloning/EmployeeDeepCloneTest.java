package com.baeldung.cloning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeDeepCloneTest {

    @Test
    void whenDeepCopy_thenSuccessful() {
        Department dept = new Department(1, "Sales");
        EmployeeDeepClone originalObj = new EmployeeDeepClone(1, dept);
        EmployeeDeepClone copyObj = new EmployeeDeepClone(originalObj);
        Assertions.assertNotNull(copyObj);
        copyObj.setId(2);
        copyObj.getDepartment()
            .setDeptId(2);
        copyObj.getDepartment()
            .setDeptName("Marketing");
        Assertions.assertTrue(originalObj.getId() != copyObj.getId());
        Assertions.assertNotSame(originalObj.getDepartment(), copyObj.getDepartment());
        Assertions.assertNotEquals(copyObj.getDepartment()
            .getDeptId(), originalObj.getDepartment()
            .getDeptId());
        Assertions.assertNotEquals(copyObj.getDepartment()
            .getDeptName(), originalObj.getDepartment()
            .getDeptName());

    }
}
