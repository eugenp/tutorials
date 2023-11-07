package com.baeldung.gson.multiplefields;

import java.util.Objects;

public class LegacyStudent {

    private String firstName;
    private String lastName;
    private String studentNumber;
    private String major;

    public LegacyStudent() {

    }

    public LegacyStudent(String firstName, String lastName, String studentNumber, String major) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.major = major;
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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
