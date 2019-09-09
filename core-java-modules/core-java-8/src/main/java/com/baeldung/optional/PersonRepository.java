package com.baeldung.optional;

public class PersonRepository {

    public String findNameById(String id) {
        return id == null ? null : "Name";
    }

}
