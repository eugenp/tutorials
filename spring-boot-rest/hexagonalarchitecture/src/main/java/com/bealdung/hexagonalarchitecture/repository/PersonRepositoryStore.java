package com.bealdung.hexagonalarchitecture.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PersonRepositoryStore {

  Map<Integer, String> persons = new HashMap<>();

  PersonRepositoryStore() {
    persons.put(1, "John");
    persons.put(2, "Bill");
  }

}
