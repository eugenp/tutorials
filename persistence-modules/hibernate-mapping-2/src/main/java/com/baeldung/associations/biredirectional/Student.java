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
 
    // getters and setters
}

