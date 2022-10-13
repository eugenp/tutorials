package com.baeldung.java_shallow_deep_copy.data;

import java.util.ArrayList;
import java.util.List;

public class PersonDeep  extends Person{

    public PersonDeep(String name, String surname, List<String> addresses) {
        this.setName(name);
        this.setSurname(surname);
        List<String> deepCopyList = new ArrayList<>();
        deepCopyList.addAll(addresses);
        this.setAddresses(deepCopyList);
    }
}
