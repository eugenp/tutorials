package com.baeldung.deepcopyarraylist;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

public class Student implements Serializable {

    private int studentId;
    private String studentName;
    private Course course;

    public Student(int studentId, String studentName, Course course) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.course = course;
    }

    public Student(Student student) {
        this.studentId = student.getStudentId();
        this.studentName = student.getStudentName();
        this.course = new Course(student.getCourse()
            .getCourseId(), student.getCourse()
            .getCourseName());
    }

    public static List<Student> deepCopyUsingCopyConstructor(List<Student> students){
        return students.stream().map(Student::new).collect(Collectors.toList());
    }

    public static List<Student> deepCopyUsingSerialization(List<Student> students){
        return students.stream().map(SerializationUtils::clone).collect(Collectors.toList());
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
