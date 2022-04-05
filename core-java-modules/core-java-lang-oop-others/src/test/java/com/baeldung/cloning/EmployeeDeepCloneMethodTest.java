package com.baeldung.cloning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeDeepCloneMethodTest {

    @Test
    void whenDeepCopyWithCloneMethod_thenSuccessful() throws CloneNotSupportedException {
        Department dept = new Department(1, "Sales");
        EmployeeDeepCloneMethod originalObj = new EmployeeDeepCloneMethod(1, dept);
        EmployeeDeepCloneMethod copyObj = (EmployeeDeepCloneMethod) originalObj.clone();
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
