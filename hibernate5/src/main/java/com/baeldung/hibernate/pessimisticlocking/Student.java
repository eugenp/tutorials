package com.baeldung.hibernate.pessimisticlocking;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    private Integer studentId;
    private String name;
    private String lastName;

    public Student() {
    }

    public Student(Integer studentId, String name, String lastName) {
        super();
        this.studentId = studentId;
        this.name = name;
        this.lastName = lastName;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
