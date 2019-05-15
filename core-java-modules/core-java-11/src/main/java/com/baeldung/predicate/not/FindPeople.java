package com.baeldung.predicate.not;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.function.Predicate.*;

public class FindPeople {
    public static void main(String[] args) {
        List<Person> people = List.of(
          new Person(1),
          new Person(18),
          new Person(2)
        );

        people.stream()
          .filter(Person::isAdult)
          .collect(Collectors.toList());

        people.stream()
          .filter(person -> !person.isAdult())
          .collect(Collectors.toList());

        people.stream()
          .filter(Person::isNotAdult)
          .collect(Collectors.toList());

        people.stream()
          .filter(not(Person::isAdult))
          .collect(Collectors.toList());
    }
}
