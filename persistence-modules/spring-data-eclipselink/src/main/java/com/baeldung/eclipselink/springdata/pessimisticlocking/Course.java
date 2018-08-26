package com.baeldung.eclipselink.springdata.pessimisticlocking;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
public class Course {

    @Id
    private Long courseId;
    private String name;
    @ManyToOne
    @JoinTable(name = "student_course")
    private Student student;

    public Course(Long courseId, String name, Student student) {
        this.courseId = courseId;
        this.name = name;
        this.student = student;
    }

    public Course() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public void setStudent(Student students) {
        this.student = students;
    }
}
