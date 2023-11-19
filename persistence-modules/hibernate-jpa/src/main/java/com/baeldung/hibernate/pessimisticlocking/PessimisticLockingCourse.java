package com.baeldung.hibernate.pessimisticlocking;

import jakarta.persistence.*;

@Entity
public class PessimisticLockingCourse {

    @Id
    private Long courseId;
    private String name;
    @ManyToOne
    @JoinTable(name = "student_course")
    private PessimisticLockingStudent student;

    public PessimisticLockingCourse(Long courseId, String name, PessimisticLockingStudent student) {
        this.courseId = courseId;
        this.name = name;
        this.student = student;
    }

    public PessimisticLockingCourse() {
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

    public PessimisticLockingStudent getStudent() {
        return student;
    }

    public void setStudent(PessimisticLockingStudent students) {
        this.student = students;
    }
}
