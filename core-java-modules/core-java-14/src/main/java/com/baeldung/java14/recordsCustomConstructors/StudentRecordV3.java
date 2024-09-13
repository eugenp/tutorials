package com.baeldung.java14.recordsCustomConstructors;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

record StudentRecordV3(String id, String name, Set<String> hobbies, Boolean active) {

    public StudentRecordV3(String name) {
        this(UUID.randomUUID().toString(), name, new HashSet<>(), true);
    }
}