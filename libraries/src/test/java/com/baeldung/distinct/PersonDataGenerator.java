package com.baeldung.distinct;

import java.util.Arrays;
import java.util.List;

public class PersonDataGenerator {

    public static List<Person> getPersonListWithFakeValues() {
        // @formatter:off
        return Arrays.asList(
            new Person(20, "Jhon", "jhon@test.com"), 
            new Person(20, "Jhon", "jhon1@test.com"), 
            new Person(20, "Jhon", "jhon2@test.com"), 
            new Person(21, "Tom", "Tom@test.com"), 
            new Person(21, "Mark", "Mark@test.com"),
            new Person(20, "Julia", "jhon@test.com"));
        // @formatter:on
    }
}
