package com.baeldung.cxf.spring;

import javax.jws.WebService;

@WebService(endpointInterface = "com.baeldung.cxf.spring.Baeldung")
public class BaeldungImpl implements Baeldung {
    private int counter;

    public String hello(String name) {
        return "Hello " + name + "!";
    }

    public String register(Student student) {
        counter++;
        return student.getName() + " is registered student number " + counter;
    }
}