package com.baeldung.hibernate.pessimisticlocking;

import javax.persistence.*;
import java.util.List;

@Entity
public class PessimisticLockingStudent {

    @Id
    private Long id;
    private String name;
    @OneToMany(mappedBy = "student")
    private List<PessimisticLockingCourse> courses;

    public PessimisticLockingStudent(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PessimisticLockingStudent() {
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

    public List<PessimisticLockingCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<PessimisticLockingCourse> courses) {
        this.courses = courses;
    }
}
