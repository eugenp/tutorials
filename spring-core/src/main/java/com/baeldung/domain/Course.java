package com.baeldung.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Course {
    private int id;
    private String name = "Physics";

    /* Dependency/collaborator */
    private Student student;

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

    public Student getStudent() {
        return student;
    }

    /* 
     * Optional dependency since a course may or 
     * may not have student enrolled into it 
     */
    @Autowired(required = false)
    public void setStudent(Student student) {
        this.student = student;
    }
}