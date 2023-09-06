package com.baeldung.shallowdeep;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

class EmployeeDeepUnitTest {

    @Test
    void whenCloningThis_thenExpectThisNotSameAsClone() {

        Role role = new Role("Software Engineer" );
        EmployeeDeep employeeDeep = new EmployeeDeep(6, role );

        EmployeeDeep employeeDeepClone = null;

        try {
            employeeDeepClone = employeeDeep.clone();
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }

        assert employeeDeepClone != null;
        employeeDeepClone.id = 7;
        employeeDeepClone.role.title = "Java Programmer";

        assertTrue(! Objects.equals( employeeDeep.id, employeeDeepClone.id ) &&
                    ! Objects.equals( employeeDeep.role.title, employeeDeepClone.role.title ) );
    }
}
