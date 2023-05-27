package com.baeldung.hibernate.optimisticlocking;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class OptimisticLockingStudent {

    @Id
    private Long id;

    private String name;

    private String lastName;
    @Version
    private Integer version;

    @OneToMany(mappedBy = "student")
    private List<OptimisticLockingCourse> courses;

    public OptimisticLockingStudent(Long id, String name, String lastName, List<OptimisticLockingCourse> courses) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.courses = courses;
    }

    public OptimisticLockingStudent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<OptimisticLockingCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<OptimisticLockingCourse> courses) {
        this.courses = courses;
    }
}
