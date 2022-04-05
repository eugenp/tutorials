package com.baeldung.easy.random.model;

import java.util.StringJoiner;

public class Person {

    private String firstName;
    private String lastName;
    private Integer age;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]").add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .add("age=" + age)
            .toString();
    }
}
