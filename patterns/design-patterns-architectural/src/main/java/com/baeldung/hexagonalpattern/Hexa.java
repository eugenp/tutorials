package com.baeldung.hexagonalpattern;


import com.baeldung.hexagonalpattern.application.ConsoleAdapter;
import com.baeldung.hexagonalpattern.domain.EmployeeCourse;
import com.baeldung.hexagonalpattern.domain.EmployeeCourseImpl;
import com.baeldung.hexagonalpattern.domain.EmployeeRepo;
import com.baeldung.hexagonalpattern.infrastructure.EmployeeRepoInMemoryAdapter;

public class Hexa {

    public static void main(String[] args) {

        /** create the Infra layer **/
        EmployeeRepo employeeRepo = new EmployeeRepoInMemoryAdapter();

        /** create the Hexagon and inject the Infra layer **/
        EmployeeCourse employeeCourse = new EmployeeCourseImpl(employeeRepo);

        /** create the Application layer and inject the hexagon port implementation**/
        ConsoleAdapter consoleAdapter = new ConsoleAdapter(employeeCourse);

        /** run the application**/
        consoleAdapter.getEmployeesForCourse("ProjectMgmt");
        consoleAdapter.getEmployeesForCourse("Architecture");
        consoleAdapter.getEmployeesForCourse("xyz");

    }

}
