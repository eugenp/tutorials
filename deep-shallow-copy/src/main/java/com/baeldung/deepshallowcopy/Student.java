package com.baeldung.deepshallowcopy;

public class Student implements Cloneable {
    private String name;

    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student(Student student) {
        this.name = student.name;
        this.age = student.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() {
        return new Student(name, age);
    }
}
