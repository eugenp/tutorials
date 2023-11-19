package com.baeldung.enumerationiteratordifferences;

import java.util.Arrays;
import java.util.List;

public final class DataUtil {

    private DataUtil() {
    }

    static List<Person> getPersons() {
        Person person1 = new Person("amit", "kumar");
        Person person2 = new Person("yogi", "kuki");
        Person person3 = new Person("raj", "dosi");
        Person person4 = new Person("prakash", "kumar");
        return Arrays.asList(person1, person2, person3, person4);
    }
}
