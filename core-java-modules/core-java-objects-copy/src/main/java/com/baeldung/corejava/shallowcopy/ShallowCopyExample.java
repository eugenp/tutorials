package com.baeldung.corejava.shallowcopy;

import com.baeldung.corejava.vo.Address;
import com.baeldung.corejava.vo.Person;

public class ShallowCopyExample {

    public static void shallowCopyExample() throws CloneNotSupportedException {
        // Original object
        Address address = new Address(321321, "India");
        Person originalPerson = new Person(1, "John", address);

        // Shallow copy by assigning object
        Person shallowCopyPerson = originalPerson.clone();

        // Modifying the shallow copy
        shallowCopyPerson.getAddress().setStreetName("Delhi");
    }
}
