package com.baeldung.shallowdeep;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Nested
    @DisplayName( "Employee.clone() tests")
    class CloneTest {

        @Test
        void whenCloningThis_thenExpectThisNotSameAsCloneUnitTest() {

            Role role = new Role("Software Engineer" );
            Employee employee = new Employee(6, role );

            Employee cEmployee = null;

            try {
                cEmployee = employee.clone();
            } catch ( CloneNotSupportedException e ) {
                e.printStackTrace();
            }

            assertNotSame( cEmployee, employee );
        }

        @Test
        void whenCloningThis_thenExpectThisClassSameAsCloneClassUnitTest() {

            Role role = new Role("Software Engineer" );
            Employee employee = new Employee(6, role );

            Employee cEmployee = null;

            try {
                cEmployee = employee.clone();
            } catch ( CloneNotSupportedException e ) {
                e.printStackTrace();
            }

            assert cEmployee != null;
            assertSame( cEmployee.getClass(), employee.getClass() );
        }
    }
}
