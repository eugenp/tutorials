package com.baeldung.nullaway;

public class NullAwayExample {

    /*
     * 1- NullAway will warn about yearsToRetirement method
     * 2- Uncomment @Nullable in printAge and NullAway will warn about this method
     * 3- Add a standard null check to be NullAway compliant
     * 4- Build will be SUCCESS
     */

    static Integer getAge(/*@Nullable*/ Person person) {
        return person.getAge();
    }

    Integer yearsToRetirement() {
        Person p = null;
        // ... p never gets set correctly...
        return 65 - getAge(p);
    }

}