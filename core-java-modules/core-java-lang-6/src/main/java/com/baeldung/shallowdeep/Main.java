package com.baeldung.shallowdeep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger logger = LoggerFactory.getLogger( Main.class );

    public static void main( String[] args ) {

        Role role = new Role("PHP Coder" );
        EmployeeShallow employeeShallow = new EmployeeShallow(2, role );

        EmployeeShallow cEmployee = null;

        // BLOCK 1
        //cEmployee = employee;

        // BLOCK 2
        try {
            cEmployee = employeeShallow.clone();
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }

        assert cEmployee != null;
        cEmployee.role.title = "Java Coder";
        cEmployee.id = 3;

        logger.info( "\n" + "Role title: " + employeeShallow.role.title + "\n" + "Role ID: " + employeeShallow.id );
    }

}
