package com.baeldung.deepcopyarraylist;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Student implements Serializable, Cloneable {

    private int studentId;
    private String studentName;
    private Course course;

    public Student() {
    }

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

    public static Student createDeepCopy(Student student) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(student), Student.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static List<Student> deepCopyUsingJackson(List<Student> students) {
        return students.stream()
            .map(Student::createDeepCopy)
            .collect(Collectors.toList());
    }

    public static List<Student> deepCopyUsingCloneable(List<Student> students) {
        return students.stream()
            .map(Student::clone)
            .collect(Collectors.toList());
    }

    public static List<Student> deepCopyUsingCopyConstructor(List<Student> students) {
        return students.stream()
            .map(Student::new)
            .collect(Collectors.toList());
    }

    public static List<Student> deepCopyUsingSerialization(List<Student> students) {
        return students.stream()
            .map(SerializationUtils::clone)
            .collect(Collectors.toList());
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

    @Override
    public Student clone() {
        Student student;
        try {
            student = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
        student.course = this.course.clone();
        return student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Student that = (Student) o;

        return Objects.equals(studentId,that.studentId) &&
            Objects.equals(studentName, that.studentName) &&
            Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId,studentName,course);
    }
}
