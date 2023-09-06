package com.baeldung.shallowdeep;

public class EmployeeShallow implements Cloneable {

    Integer id;
    Role role;

    EmployeeShallow( Integer id, Role role ) {
        this.id = id;
        this.role = role;
    }

    @Override
    public EmployeeShallow clone() throws CloneNotSupportedException {

        return ( EmployeeShallow ) super.clone();
    }
}
