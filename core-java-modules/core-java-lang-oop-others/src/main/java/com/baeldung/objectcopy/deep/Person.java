package com.baeldung.objectcopy.deep;

public class Person implements Cloneable {
    private String name;
    private Department department;
    private int age;

    public Person(String name, Department department, int age) {
        this.name = name;
        this.department = department;
        this.age = age;
    }

    public Person(Person person) {
        this(person.getName(), new Department(person.getDepartment()), person.getAge());
    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        Person deepCopyPerson = (Person) super.clone();
        deepCopyPerson.department = this.department.clone();
        return deepCopyPerson;
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
