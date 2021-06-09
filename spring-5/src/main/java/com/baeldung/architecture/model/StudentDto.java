package com.baeldung.architecture.model;

public class StudentDto {
    private Long id;
    private String name;

    // getter and setter
    public Long getId() {
        return id;
    }

    public StudentDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StudentDto setName(String name) {
        this.name = name;
        return this;
    }
}
