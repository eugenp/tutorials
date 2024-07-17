package com.baeldung.deepcopyshallowcopy;

import java.util.ArrayList;
import java.util.List;


public class Person {
    public  String name;
    public List<String> hobbies;

    public Person(String name, List<String> hobbies) {
        this.name = name;
        this.hobbies = new ArrayList<>(hobbies); // create a new list to ensure deep copy
    }

    public Person(Person originalPerson) {
        this.name = originalPerson.name;
        this.hobbies = new ArrayList<>(originalPerson.hobbies); // create a new list and copy each element
    }
}
