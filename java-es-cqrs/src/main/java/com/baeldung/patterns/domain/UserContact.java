package com.baeldung.patterns.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class UserContact {

    private Set<Contact> contacts = new HashSet<>();
    private Map<String, Set<Contact>> contactByType = new HashMap<>();

}
