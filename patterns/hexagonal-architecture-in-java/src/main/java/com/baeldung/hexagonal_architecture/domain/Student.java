package com.baeldung.hexagonal_architecture.domain;

public class Student {

    private Long studentID;
    private String studentName;
    private String studentMail;

    public Student(Long studentID, String studentName, String studentMail) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentMail = studentMail;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMail() {
        return studentMail;
    }

    public void setStudentMail(String studentMail) {
        this.studentMail = studentMail;
    }

}