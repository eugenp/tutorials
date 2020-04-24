package com.baeldung.architecture.hexagonal.dao.model;

public class Student {

    private int roll;

    private String name;

    private int className;

    public int getRoll() {
        return this.roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassName() {
        return this.className;
    }

    public void setClassName(int className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "roll=" + roll +
                ", name='" + name + '\'' +
                ", className=" + className +
                '}';
    }
}
