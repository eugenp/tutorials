package com.baeldung.objectsize;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Professor {

    private String name;
    private boolean tenured;
    private List<Course> courses;
    private int level;
    private LocalDate birthDay;
    private double lastEvaluation;

    public Professor(String name, boolean tenured, List<Course> courses,
      int level, LocalDate birthDay, double lastEvaluation) {
        this.name = name;
        this.tenured = tenured;
        this.courses = courses;
        this.level = level;
        this.birthDay = birthDay;
        this.lastEvaluation = lastEvaluation;
    }
}
