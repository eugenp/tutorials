package com.baeldung.java14.recordscustomconstructors;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

record StudentRecordV3(String id, String name, Set<String> hobbies, boolean active) {

    public StudentRecordV3(String name) {
        this(UUID.randomUUID().toString(), name, new HashSet<>(), true);
    }
}