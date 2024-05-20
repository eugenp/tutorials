package com.baeldung.corejava.deepcopy;

import com.baeldung.corejava.vo.Person;

public class DeepCopyExample {

    public static void deepCopyExample() {
        Person originalPerson = new Person("John", "123 Main St");

        // Deep copy by copying object values
        Person deepCopyPerson = new Person(originalPerson.getName(), originalPerson.getAddress());

        // Modifying the deep copy
        deepCopyPerson.setName("Jane");

        // Original object remains unchanged
        System.out.println("Original Person: " + originalPerson.getName());
        System.out.println("Deep Copy Person: " + deepCopyPerson.getName());
    }

}
