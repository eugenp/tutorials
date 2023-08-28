package com.baeldung.shallowdeep;

public class Main {

    public static void main(String[] args) {

        Role role = new Role("PHP Coder" );
        Employee employee = new Employee(2, role );

        Employee cEmployee = null;

        // BLOCK 1
        //cEmployee = employee;

        // BLOCK 2
        try {
            cEmployee = employee.clone();
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }

        cEmployee.role.title = "Java Coder";
        cEmployee.id = 3;
        System.out.println( "Role title: " + employee.role.title + "\n" + "Role ID: " + employee.id );
    }
}
