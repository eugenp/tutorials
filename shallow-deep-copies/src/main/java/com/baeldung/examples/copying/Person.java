package com.baeldung.examples.copying;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Optional.ofNullable;

final class Person implements Serializable {

    private int age;
    private String name;
    private Person bestFriend;

    public Person() {
    }

    Person(String name, int age) {
        this();
        this.name = name;
        this.age = age;
    }

    Person(String name, int age, Person bestFriend) {
        this(name, age);
        this.bestFriend = bestFriend;
    }

    Person copy() { return new Person( name,
            age, ofNullable(bestFriend).map(Person::copy).orElse(null) ); }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getBestFriend() {
        return bestFriend;
    }

    public void setBestFriend(Person bestFriend) {
        this.bestFriend = bestFriend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(bestFriend, person.bestFriend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, bestFriend);
    }
}
