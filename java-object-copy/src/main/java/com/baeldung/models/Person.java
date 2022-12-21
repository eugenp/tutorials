package com.baeldung.models;
public class Person {
    private int age;
    private Car car;
    private String name;

    public Person(Person p) {
        this(p.name, p.age, new Car(p.getCar()));
    }

    Person(String name, int age, Car car1) {
        this.age = age;
        this.name = name;
        car = car1;
    }

    public Car getCar() {
        return car;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setName(String name) {
        this.name = name;
    }
}