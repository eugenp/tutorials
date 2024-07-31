package com.baeldung.timefoldsolver;

import java.util.Set;

public class Employee {

    private String name;
    private Set<String> skills;

    public Employee(String name, Set<String> skills) {
        this.name = name;
        this.skills = skills;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public Set<String> getSkills() {
        return skills;
    }

}
