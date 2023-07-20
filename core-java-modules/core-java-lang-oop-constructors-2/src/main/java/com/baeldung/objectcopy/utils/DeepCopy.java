package com.baeldung.objectcopy.utils;

import com.baeldung.objectcopy.objects.Person;

public class DeepCopy {
    public static void main(String[] args) {

        // Original copy
        Person entry = new Person("John Bull", 69);

        // Deep copy (separate object)
        Person deepCopy = new Person(entry);

        // Modify the object's name in the original copy
        entry.setName("John Donne Bull");

        // Print the names of the original and deep copy
        System.out.println(entry.getName()); // Output: John Donne Bull
        System.out.println(deepCopy.getName()); // Output: John Bull
    }
}
