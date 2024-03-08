package com.baeldung;

public class Grade {
    private String courseCode;
    private String grade;

    public Grade() {
    }

    public Grade(String courseCode, String grade) {
        this.courseCode = courseCode;
        this.grade = grade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
