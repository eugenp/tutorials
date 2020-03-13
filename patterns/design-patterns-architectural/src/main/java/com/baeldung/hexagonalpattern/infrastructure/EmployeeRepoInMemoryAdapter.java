package com.baeldung.hexagonalpattern.infrastructure;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.hexagonalpattern.domain.EmployeeRepo;



public class EmployeeRepoInMemoryAdapter implements EmployeeRepo {
    private static Map<String,String> courseToEmployeesMap = new HashMap<String, String>();

    static {
        courseToEmployeesMap.put("Architecture","Jatin,Michael");
        courseToEmployeesMap.put("ProjectMgmt","Michael");
    }
    public String getEmployees(String courseId) {
        return courseToEmployeesMap.get(courseId);
    }

}
