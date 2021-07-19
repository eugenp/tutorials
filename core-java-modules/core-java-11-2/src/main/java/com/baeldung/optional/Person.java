package com.baeldung.optional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Person {
    private String name;
    private int age;
    private String password;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Integer> getAge() {
        return Optional.of(age);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public static List<Person> search(List<Person> people, String name, Optional<Integer> age) {
        // Null checks for people and name
        return people.stream()
                .filter(p -> p.getName().equals(name))
                .filter(p -> p.getAge().get() >= age.orElse(0))
                .collect(Collectors.toList());
    }

    public static List<Person> search(List<Person> people, String name, Integer age) {
        // Null checks for people and name
        final Integer ageFilter = age != null ? age : 0;

        return people.stream()
                .filter(p -> p.getName().equals(name))
                .filter(p -> p.getAge().get() >= ageFilter)
                .collect(Collectors.toList());
    }

    public static List<Person> search(List<Person> people, String name) {
        return doSearch(people, name, 0);
    }

    public static List<Person> search(List<Person> people, String name, int age) {
        return doSearch(people, name, age);
    }

    private static List<Person> doSearch(List<Person> people, String name, int age) {
        // Null checks for people and name
        return people.stream()
                .filter(p -> p.getName().equals(name))
                .filter(p -> p.getAge().get().intValue() >= age)
                .collect(Collectors.toList());
    }
}
