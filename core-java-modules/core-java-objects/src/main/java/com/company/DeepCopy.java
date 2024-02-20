package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeepCopy {
    public static void main(String[] args) {

        List<Person> johnFriends = new ArrayList<>(Arrays.asList(new Person("John", 35)));
        Person originalPerson = new Person("Peter", 37, johnFriends);

        Person deepCopyOfPerson = originalPerson.deepCopy();
        deepCopyOfPerson.getFriends().add(new Person("Jane", 38));

        System.out.println("Original Person Friends Count:" + originalPerson.getFriends().size());      // Output: 1
        System.out.println("Deep Copy Friends Count:" + deepCopyOfPerson.getFriends().size());     // Output: 2
    }
}
