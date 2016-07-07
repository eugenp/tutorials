package com.baeldung.cxf.introduction;

import java.util.Map;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@WebService
public interface Baeldung {
    String hello(String name);

    String helloStudent(Student student);

    @XmlJavaTypeAdapter(StudentMapAdapter.class)
    Map<Integer, Student> getStudents();
}