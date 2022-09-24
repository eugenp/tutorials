package com.baeldung.compare;

import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;

public class CompareToBuilderExample implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        return new CompareToBuilder()
          .append(o1.getName(), o2.getName())
          .append(o1.getAge(), o2.getAge())
          .build();
    }
}
