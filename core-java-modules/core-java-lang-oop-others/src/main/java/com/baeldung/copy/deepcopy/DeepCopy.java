package com.baeldung.copy.deepcopy;

import com.baeldung.copy.Person;

import java.util.ArrayList;
import java.util.List;

public class DeepCopy {

    public static void main(String[] args){

        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        hobbies.add("Gardening");

        Person originalPerson = new Person("Alice", hobbies);
        Person deepCopy = new Person(originalPerson.getName(), new ArrayList<>(originalPerson.getHobbies()));
    }
}
