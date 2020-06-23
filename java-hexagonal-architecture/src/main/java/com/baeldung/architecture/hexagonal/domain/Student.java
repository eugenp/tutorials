package com.baeldung.architecture.hexagonal.domain;

public class Student {

    private Long id;
    private String name;
    private float score;
    private String performanceRating;

    public Student(String name, float score) {
        this.name = name;
        this.score = score;
    }

    public void evaluatePerformance() {
        if (score > 9.5) {
            performanceRating = "VERY HIGH";
        } else if (score < 5.5) {
            performanceRating = "VERY POOR";
        } else {
            performanceRating = "AVERAGE";
        }
    }

    // getters/setters

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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(String performanceRating) {
        this.performanceRating = performanceRating;
    }
}
