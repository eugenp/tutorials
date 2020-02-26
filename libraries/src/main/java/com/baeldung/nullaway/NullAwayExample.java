package com.baeldung.nullaway;

import com.baeldung.distinct.Person;

public class NullAwayExample {

    // NullAway will warn about this method, add a @Nullable annotation to the method parameter to make the build fail
    static void printAge(Person person) {
        System.out.println(person.getAge());
    }

}