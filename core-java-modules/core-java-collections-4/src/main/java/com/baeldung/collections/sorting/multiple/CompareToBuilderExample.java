package com.baeldung.collections.sorting.multiple;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class CompareToBuilderExample implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return new CompareToBuilder()
          .append(o1.getName(), o2.getName())
          .append(o1.getAge(), o2.getAge())
          .build();
    }
}
