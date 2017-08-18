package com.baeldung.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Student {

    private static final Logger LOG = LoggerFactory.getLogger(Student.class.getName());

    private int id;
    private String name;
    private School school;

    public Student() {
    }

    public Student(int id, String name, School school) {
        this.id = id;
        this.name = name;
        this.school = school;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void sayHello() {
        LOG.info("Hi! I am " + name + ". My school is : " + school.getName() + ". My ID : " + id);
    }
}
