package com.baeldung.hamcrest.listcontainitems;

import java.util.List;

public class Developer {
    private String name;
    private int age;
    private String os;
    private List<String> languages;

    public Developer(String name, int age, String os, List<String> languages) {
        this.name = name;
        this.age = age;
        this.os = os;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getOs() {
        return os;
    }

    public List<String> getLanguages() {
        return languages;
    }
}