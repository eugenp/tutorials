package com.baeldung.junit;
public class Students implements Cloneable {
    String name;
    Courses course;
    public Students(String name, Courses course) {
        this.name = name;
        this.course = course;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
