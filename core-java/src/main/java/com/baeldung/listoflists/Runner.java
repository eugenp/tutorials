package com.baeldung.listoflists;

/**
 * Runner
 */
public class Runner {
    private String name;
    private Gender gender;
    private int birthYear;

    public Runner(String name, Gender gender, int birthYear) {
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public double getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
