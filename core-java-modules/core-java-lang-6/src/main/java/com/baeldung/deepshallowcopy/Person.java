package com.baeldung.deepshallowcopy;

import java.util.ArrayList;
import java.util.List;

public class Person implements Cloneable {
    private String name;
    private int age;
    private List<String> hobbies; // Mutable property

    public Person(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
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

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        Person clonedPerson = (Person) super.clone();
        clonedPerson.hobbies = new ArrayList<>(this.hobbies);
        return clonedPerson;
    }
}