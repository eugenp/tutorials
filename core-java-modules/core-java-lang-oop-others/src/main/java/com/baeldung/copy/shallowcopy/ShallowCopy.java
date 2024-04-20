package com.baeldung.copy.shallowcopy;

import com.baeldung.copy.Person;

import java.util.ArrayList;
import java.util.List;

public class ShallowCopy {

    public static void main(String[] args){

        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        hobbies.add("Gardening");

        Person originalPerson = new Person("Alice", hobbies);
        Person shallowCopy = new Person(originalPerson.getName(), originalPerson.getHobbies());
    }
}
