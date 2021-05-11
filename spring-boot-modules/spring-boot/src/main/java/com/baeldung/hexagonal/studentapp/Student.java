package com.baeldung.hexagonal.studentapp;

public class Student {
    private Long   id;
    private String name;
    private String group;

    public Student(Long id, String name, String group) {
        this.id    = id;
        this.name  = name;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public Student setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public Student setGroup(String group) {
        this.group = group;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
