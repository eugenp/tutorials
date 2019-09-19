package com.baeldung.core.modifiers.otherpackage;

import com.baeldung.core.modifiers.Person;

public class Sword {
    Person wielder;
    String name;

    public Sword(String wielderName, int wielderAge, String swordName) {

        name = swordName;

        // The constructor Person(String, String, int) is not visible

        // person = new Person("Mr", wielderName, wielderAge);
        wielder = new Person();
    }
}
