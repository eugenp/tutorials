package com.baeldung.spring.jdbc.queryforlist;

import java.util.Objects;

public class Student {

    private Integer id;
    private String name;
    private String major;

    public Student() {
    }

    public Student(Integer id, String name, String major) {
        this.id = id;
        this.name = name;
        this.major = major;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student student)) {
            return false;
        }
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(major, student.major);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, major);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }


}