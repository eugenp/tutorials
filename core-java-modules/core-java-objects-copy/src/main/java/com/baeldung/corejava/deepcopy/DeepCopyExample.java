package com.baeldung.corejava.deepcopy;

import com.baeldung.corejava.vo.Address;
import com.baeldung.corejava.vo.Person;

public class DeepCopyExample {

    public static void deepCopyExample() {
        Address address = new Address(321321, "India");
        Person originalPerson = new Person(1, "John", address);

        // Deep copy by copying object values
        Person deepCopyPerson = new Person(originalPerson.getPersonId(), originalPerson.getName(), new Address(originalPerson.getAddress()
            .getPinCode(), originalPerson.getAddress()
            .getStreetName()));

        // Modifying the deep copy
        deepCopyPerson.getAddress()
            .setStreetName("Delhi");
    }

}
