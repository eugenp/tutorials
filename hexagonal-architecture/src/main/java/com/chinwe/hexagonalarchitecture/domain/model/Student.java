package com.chinwe.hexagonalarchitecture.domain.model;

import java.util.Objects;

public class Student {
    private String studentName;
    private Long studentId;
    private Long studentRegNumber;

    public Student(String studentName, Long studentId, Long studentRegNumber) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentRegNumber = studentRegNumber;
    }

    public Student() {
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentRegNumber() {
        return studentRegNumber;
    }

    public void setStudentRegNumber(Long studentRegNumber) {
        this.studentRegNumber = studentRegNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentName, student.studentName) &&
                Objects.equals(studentId, student.studentId) &&
                Objects.equals(studentRegNumber, student.studentRegNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, studentId, studentRegNumber);
    }
}
