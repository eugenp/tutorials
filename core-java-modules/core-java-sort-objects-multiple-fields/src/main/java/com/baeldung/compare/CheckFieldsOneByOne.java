package com.baeldung.compare;

import java.util.Comparator;

public class CheckFieldsOneByOne implements Comparator<Employee> {
    @Override
    public int compare(Employee o1, Employee o2) {
        int nameCompare = o1.getName() == null ? -1 :
          o1.getName().compareTo(o2.getName());
        if(nameCompare != 0) {
            return nameCompare;
        }
        return Integer.compare(o1.getAge(), o2.getAge());
    }
}
