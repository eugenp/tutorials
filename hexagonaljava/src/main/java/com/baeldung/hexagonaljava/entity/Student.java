package com.baeldung.hexagonaljava.entity;

import java.util.Map;

public class Student {

    private Integer id;

    private String name;

    private Map<String, Double> marks;

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

    public Map<String, Double> getMarks() {
        return marks;
    }

    public void setMarks(Map<String, Double> marks) {
        this.marks = marks;
    }
}
