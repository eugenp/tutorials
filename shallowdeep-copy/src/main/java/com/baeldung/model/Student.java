package com.baeldung.model;

import java.util.ArrayList;
import java.util.List;

public class Student implements Cloneable {
    private int age;
    private List<String> subjects = new ArrayList<>();

    @Override
    public Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void addSubject(String subject){
        subjects.add(subject);
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

}
