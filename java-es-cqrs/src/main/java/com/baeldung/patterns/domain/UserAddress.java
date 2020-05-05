package com.baeldung.patterns.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;

@Data
public class UserAddress {

    private Set<Address> addresses = new HashSet<>();
    private Map<String, Set<Address>> addressByRegion = new HashMap<>();

}
