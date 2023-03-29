package com.baeldung.copies;

import java.util.ArrayList;
import java.util.List;

public class PersonDeep {
    private String name;
    private List<String> hobbies;

    public PersonDeep(String name, List<String> hobbies) {
        this.name = name;
        this.hobbies = hobbies;
    }

    // Copy constructor for deep copy
    public PersonDeep(PersonDeep person) {
        this.name = person.name;
        // Creating a new ArrayList object with copied elements of the original List object
        this.hobbies = new ArrayList<>(person.hobbies);
    }

    public String getName() {
        return name;
    }
    public List<String> getHobbies() {
        return hobbies;
    }
}
