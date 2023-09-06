package com.baeldung.shallowdeep;

public class EmployeeDeep implements Cloneable {

    Integer id;
    Role role;

    EmployeeDeep( Integer id, Role role ) {
        this.id = id;
        this.role = role;
    }

    @Override
    public EmployeeDeep clone() throws CloneNotSupportedException {

        EmployeeDeep cEmployeeDeep = ( EmployeeDeep ) super.clone();
        cEmployeeDeep.role = role.clone();

        return cEmployeeDeep;
    }

}
