package com.baeldung.hexagonalpattern.application;

import com.baeldung.hexagonalpattern.domain.EmployeeCourse;

public class ConsoleAdapter {
    private EmployeeCourse employeeCourse;

    public ConsoleAdapter(EmployeeCourse employeeCourse) {
        super();
        this.employeeCourse = employeeCourse;
    }

    public void getEmployeesForCourse(String courseId) {
        System.out.println(employeeCourse.getEmployeesForCourse(courseId));
    }
}
