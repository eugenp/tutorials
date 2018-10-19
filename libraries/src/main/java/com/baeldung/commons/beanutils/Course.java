package com.baeldung.commons.beanutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {
    private String name;
    private List<String> codes;
    private Map<String, Student> enrolledStudent = new HashMap<String, Student>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public void setEnrolledStudent(String enrolledId, Student student) {
        enrolledStudent.put(enrolledId, student);
    }

    public Student getEnrolledStudent(String enrolledId) {
        return enrolledStudent.get(enrolledId);
    }
}
