package com.baeldung.nullaway;

import com.baeldung.distinct.Person;

public class NullAwayExample {

    /*
     * 1- NullAway will warn about showPersonAge method
     * 2- Uncomment @Nullable in printAge and NullAway will warn about this method
     * 3- Add a standard null check to be NullAway compliant
     * 4- Build will be SUCCESS
     */

    static Integer printAge(/*@Nullable*/ Person person) {
        return person.getAge();
    }

    static void showPersonAge() {
        printAge(null);
    }

}