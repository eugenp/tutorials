package com.baeldung.patterns.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class UserContact {

    private Map<String, Set<Contact>> contactByType = new HashMap<>();

}
