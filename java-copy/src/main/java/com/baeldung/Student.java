package com.baeldung;

import java.util.List;

public class Student {
    private String firstName;
    private String lastName;
    private List<Grade> grades;

    public Student() {
    }
    public Student(String firstName, String lastName, List<Grade> grades) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grades = grades;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
