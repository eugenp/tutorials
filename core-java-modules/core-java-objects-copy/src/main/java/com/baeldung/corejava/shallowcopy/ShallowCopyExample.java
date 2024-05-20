package com.baeldung.corejava.shallowcopy;

import com.baeldung.corejava.vo.Person;

public class ShallowCopyExample {

    public static void shallowCopyExample() {
        // Original object
        Person originalPerson = new Person("John", "123 Main St");

        // Shallow copy by assigning object
        Person shallowCopyPerson = originalPerson;

        // Modifying the shallow copy
        shallowCopyPerson.setName("Jane");

        // Original object gets changed
        System.out.println("Original Person: " + originalPerson.getName());
        System.out.println("Shallow Copy Person: " + shallowCopyPerson.getName());
    }
}
