package com.company;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Person implements Cloneable {
    private String name;
    private int age;
    private List<Person> friends;

    public Person(String n, int a, List<Person> f) {
        name = n;
        age = a;
        friends = f;
    }

    public Person(String n, int a) {
        name = n;
        age = a;

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

    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Person deepCopy() {
        Person result;

        result = (Person) this.clone();

        final List<Person> friends = result.getFriends();
        if (friends != null) {
            result.setFriends(friends.stream()
                    .map(p -> p.deepCopy())
                    .collect(Collectors.toList()));
        } else {

            result.setFriends(Collections.emptyList());
        }

        return result;
    }
}

