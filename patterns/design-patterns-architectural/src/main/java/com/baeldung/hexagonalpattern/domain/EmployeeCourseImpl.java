package com.baeldung.hexagonalpattern.domain;

public class EmployeeCourseImpl implements EmployeeCourse {

    private EmployeeRepo employeeRepo;

    public EmployeeCourseImpl(EmployeeRepo employeeRepo) {
        super();
        this.employeeRepo = employeeRepo;
    }

    public EmployeeCourseImpl() {
    }

    public String getEmployeesForCourse(String courseId) {
        String result = employeeRepo.getEmployees(courseId);
        if(null == result || result.trim().length() <=0) {
            result = "Invalid course entered !";
        }else {
            result = "List of employees enrolled for the course " + courseId + " are: " + result;
        }
        return result;
    }
}
