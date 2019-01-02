package com.baeldung.jackson.deserialization.immutable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = MaritalAwarePerson.Builder.class)
public class MaritalAwarePerson {

    private final String name;
    private final int age;
    private final Boolean married;

    private MaritalAwarePerson(String name, int age, Boolean married) {
        this.name = name;
        this.age = age;
        this.married = married;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Boolean getMarried() {
        return married;
    }

    @JsonPOJOBuilder
    static class Builder {
        String name;
        int age;
        Boolean married;

        Builder withName(String name) {
            this.name = name;
            return this;
        }

        Builder withAge(int age) {
            this.age = age;
            return this;
        }

        Builder withMarried(boolean married) {
            this.married = married;
            return this;
        }

        public MaritalAwarePerson build() {
            return new MaritalAwarePerson(name, age, married);
        }


    }
}
