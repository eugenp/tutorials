package com.baeldung.jest;

import java.util.List;

public class Employee {
    String name;
    String title;
    List<String> skills;
    int years_of_service;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public int getYears_of_service() {
        return years_of_service;
    }

    public void setYears_of_service(int years_of_service) {
        this.years_of_service = years_of_service;
    }
}
