package com.baeldung.cxf.introduction;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.jws.WebService;

@WebService(endpointInterface = "com.baeldung.cxf.introduction.Baeldung")
public class BaeldungImpl implements Baeldung {
    private Map<Integer, Student> students = new LinkedHashMap<Integer, Student>();

    public String hello(String name) {
        return "Hello " + name;
    }

    public String helloStudent(Student student) {
        students.put(students.size() + 1, student);
        return "Hello " + student.getName();
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }
}