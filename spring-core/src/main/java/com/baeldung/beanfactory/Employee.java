package com.baeldung.beanfactory;

public class Employee {

    private String name;
    private int age;
    private long salary;
    private String type;
    private String title;

    public Employee(long salary) {
        this.salary = salary;
    }

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Employee() {
    }

    public Employee(long salary, String type) {
        this.salary = salary;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getsalary() {
        return salary;
    }

    public void setsalary(long salary) {
        this.salary = salary;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public void show() {
        System.out.println("------------------" + "\n" + "Title:" + title + "\n" + "Salary:" + salary + "\n" + "Type:" + type);
    }
}
