package com.baeldung.optional;

import javax.annotation.Nullable;

public class PersonRepository {

    @Nullable
    public String findNameById(String id) {
        return id == null ? null : "Name";
    }

}