package com.baeldung.associations.biredirectional;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Student {
 
    @Id
    private Long id;
 
    private String name;
 
    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    
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

