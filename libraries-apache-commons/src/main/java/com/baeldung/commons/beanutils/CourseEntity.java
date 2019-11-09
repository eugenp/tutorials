package com.baeldung.commons.beanutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseEntity {
    private String name;
    private List<String> codes;
    private Map<String, Student> students = new HashMap<String, Student>();

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

    public void setStudent(String id, Student student) {
        students.put(id, student);
    }

    public Student getStudent(String enrolledId) {
        return students.get(enrolledId);
    }
}
