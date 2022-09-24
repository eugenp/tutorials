package com.baeldung.objectcopy.shallow;

public class Person implements Cloneable {
    private String name;
    private Department department;
    private int age;

    public Person(String name, Department department, int age) {
        this.name = name;
        this.department = department;
        this.age = age;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
