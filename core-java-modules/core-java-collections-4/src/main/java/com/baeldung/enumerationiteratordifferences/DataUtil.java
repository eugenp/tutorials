package com.baeldung.enumeration_iterator_differences;

import java.util.List;

public final class DataUtil {

    private DataUtil() {
    }

    static List<Person> getPersons() {
        Person person1 = new Person("amit", "kumar");
        Person person2 = new Person("yogi", "kuki");
        Person person3 = new Person("raj", "dosi");
        Person person4 = new Person("prakash", "kumar");
        return List.of(person1, person2, person3, person4);
    }
}
