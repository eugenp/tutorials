package com.baeldung.collections.sorting.multiple;

import java.util.Comparator;


public class CheckFieldsOneByOne implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        int nameCompare;
        if(o1.getName() == null) {
            nameCompare = o2.getName() == null ? 0 : -1;
        } else {
            nameCompare = o2.getName() == null ? 1 : o1.getName().compareTo(o2.getName());
        }
        if(nameCompare != 0) {
            return nameCompare;
        }
        return Integer.compare(o1.getAge(), o2.getAge());
    }
}
