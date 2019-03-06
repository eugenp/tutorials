package com.baeldung.jackson.deserialization.immutable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Person.Builder.class)
public class Person {

    private final String name;
    private final Integer age;

    private Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @JsonPOJOBuilder
    static class Builder {
        String name;
        Integer age;

        Builder withName(String name) {
            this.name = name;
            return this;
        }

        Builder withAge(Integer age) {
            this.age = age;
            return this;
        }

        Person build() {
            return new Person(name, age);
        }
    }
}
