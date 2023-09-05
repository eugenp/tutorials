package com.baeldung.shallowdeep;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

class EmployeeShallowUnitTest {

    @Test
    void whenCloningThis_thenExpectThisNotSameAsClone() {

        Role role = new Role("Software Engineer" );
        EmployeeShallow employeeShallow = new EmployeeShallow(6, role );

        EmployeeShallow employeeShallowClone = null;

        try {
            employeeShallowClone = employeeShallow.clone();
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }

        assert employeeShallowClone != null;
        employeeShallowClone.id = 7;
        employeeShallowClone.role.title = "Java programmer";

        assertTrue(! Objects.equals( employeeShallow.id, employeeShallowClone.id ) &&
                    Objects.equals( employeeShallow.role.title, employeeShallowClone.role.title ) );
    }
}
