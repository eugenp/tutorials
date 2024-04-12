package com.baeldung.applicationcontext;

public class Student {

    private int no;
    private String name;

    public Student() {
    }

    public Student(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void destroy() {
        System.out.println("Student(no: " + no + ") is destroyed");
    }
}
