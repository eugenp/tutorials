package com.baeldung.shallowdeep;

public class Employee implements Cloneable {

    Integer id;
    Role role;

    Employee( Integer id, Role role ) {
        this.id = id;
        this.role = role;
    }
/*
    // BLOCK 3 [1]
    @Override
    public Employee clone() throws CloneNotSupportedException {

        Employee cEmployee = (Employee) super.clone();

        return cEmployee;
    }
*/

    // BLOCK 4 [2]
    @Override
    public Employee clone() throws CloneNotSupportedException {

        Employee cEmployee = (Employee) super.clone();
        cEmployee.role = role.clone();

        return cEmployee;
    }

}
