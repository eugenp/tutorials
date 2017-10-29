package com.baeldung.setterdi.domain;

public class BioClass {

    private int numberOfStudent;
    private int numberOfTeacher;

    public int getNumberOfStudent() {
        return numberOfStudent;
    }

    public void setNumberOfStudent(int numberOfStudent) {
        this.numberOfStudent = numberOfStudent;
    }

    public int getNumberOfTeacher() {
        return numberOfTeacher;
    }

    public void setNumberOfTeacher(int numberOfTeacher) {
        this.numberOfTeacher = numberOfTeacher;
    }

    public void giveBioClassInfo() {
        System.out.println("Bio Class Info with setter DI");
        System.out.println("No. of Student: " + getNumberOfStudent() + " No. of teachers: " + getNumberOfTeacher());
    }
}
