package com.baeldung.beaninjection.constructor;

public class Person {

    public String name;
    public Integer age;
    public Hobby hobby;

    public Person(String name, Integer age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + ", hobby=" + hobby + '}';
    }
}
