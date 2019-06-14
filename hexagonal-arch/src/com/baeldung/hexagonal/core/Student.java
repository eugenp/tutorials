package com.baeldung.hexagonal.core;

import java.util.Map;

public class Student {
    
    private Integer id;
    private String name;
    private Map<String,Integer> grades;
    
    
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
    public Map<String, Integer> getGrades() {
        return grades;
    }
    public void setGrades(Map<String, Integer> grades) {
        this.grades = grades;
    }
    

}
