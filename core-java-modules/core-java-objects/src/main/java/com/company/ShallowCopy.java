package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShallowCopy {

    public static void main(String[] args) {

        List<Person> johnFriends = new ArrayList<>(Arrays.asList(new Person("John", 35)));
        Person originalPerson = new Person("Peter", 37, johnFriends);

        Person shallowCopy = originalPerson;

        shallowCopy.getFriends().add(new Person("Jane", 38));

        System.out.println("Original Person Friends Count:" + originalPerson.getFriends().size());
        System.out.println("Shallow Copy Friends Count:" + shallowCopy.getFriends().size());

    }
}
