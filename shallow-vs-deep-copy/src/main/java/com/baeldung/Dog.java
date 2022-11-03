package com.baeldung;

public class Dog implements Cloneable {
    String name;
    int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Dog(Dog dog) {
        this.name = dog.name;
        this.age = dog.age;
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

    @Override
    public Dog clone() {
        return (Dog) super.clone();
    }
}