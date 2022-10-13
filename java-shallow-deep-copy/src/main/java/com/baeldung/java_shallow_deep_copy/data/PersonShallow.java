package com.baeldung.java_shallow_deep_copy.data;

import java.util.List;

public class PersonShallow extends Person {

    public PersonShallow(String name, String surname, List<String> addresses) {
        this.setName(name);
        this.setSurname(surname);
        this.setAddresses(addresses);
    }


}
