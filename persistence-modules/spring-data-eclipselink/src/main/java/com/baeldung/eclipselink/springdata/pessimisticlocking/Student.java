package com.baeldung.eclipselink.springdata.pessimisticlocking;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Student {

    @Id
    private Long id;
    private String name;
    @OneToMany(mappedBy = "student")
    private List<Course> courses;

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student() {
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
