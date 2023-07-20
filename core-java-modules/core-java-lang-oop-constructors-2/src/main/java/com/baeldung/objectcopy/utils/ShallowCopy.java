package com.baeldung.objectcopy.utils;

import com.baeldung.objectcopy.objects.Person;

public class ShallowCopy {
    public static void main(String[] args) {

        // Original copy
        Person entry = new Person("John Bull", 69);

        // Shallow copy (same reference)
        Person shallowCopy = entry;

        // Modify the object's name in the original copy
        entry.setName("John Donne Bull");

        // Print the names of the original and shallow copy
        System.out.println(entry.getName()); // Output: John Donne Bull
        System.out.println(shallowCopy.getName()); // Output: John Donne Bull
    }
}
