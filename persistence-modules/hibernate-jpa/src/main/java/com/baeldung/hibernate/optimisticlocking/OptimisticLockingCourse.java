package com.baeldung.hibernate.optimisticlocking;

import jakarta.persistence.*;

@Entity
public class OptimisticLockingCourse {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinTable(name = "optimistic_student_course")
    private OptimisticLockingStudent student;

    public OptimisticLockingCourse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public OptimisticLockingCourse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OptimisticLockingStudent getStudent() {
        return student;
    }

    public void setStudent(OptimisticLockingStudent student) {
        this.student = student;
    }
}
