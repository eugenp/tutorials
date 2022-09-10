package com.baeldung.streams.multiplefilters;

import java.util.List;
import java.util.stream.Collectors;

public class Student {

    private String name;
    private int year;
    private List<Integer> marks;
    private Profile profile;

    public enum Profile {
        COMPUTER_SCIENCE,
        MATH,
        PHYSICS
    }

    public double getMarksAverage() {
        return marks.stream().collect(Collectors.averagingInt(m -> m));
    }

    public boolean isEligibleForScholarship() {
        return getMarksAverage() > 50
            && marks.size() > 3
            && profile != Profile.PHYSICS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Integer> getMarks() {
        return marks;
    }

    public void setMarks(List<Integer> marks) {
        this.marks = marks;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}