package com.baeldung.model;

public class Student {

    private int id;
    private String name;
    private College college;

    public Student() {
    }

    public Student(int id, String name, College college) {
        this.id = id;
        this.name = name;
        this.college = college;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public void introduceMyself() {
        System.out.println("My ID is " + id + " and my name is " + name + ". I am a student at " + college.getName() + " College.");
    }

}
