package com.baeldung.drools.model;

public class Applicant {

    private String name;
    private int age;
    private double currentSalary;
    private int experienceInYears;

    public Applicant(String name, int age, Double currentSalary, int experienceInYears) {
        this.name = name;
        this.age = age;
        this.currentSalary = currentSalary;
        this.experienceInYears = experienceInYears;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(Double currentSalary) {
        this.currentSalary = currentSalary;
    }

    public int getExperienceInYears() {
        return experienceInYears;
    }

    public void setExperienceInYears(int experienceInYears) {
        this.experienceInYears = experienceInYears;
    }
}
