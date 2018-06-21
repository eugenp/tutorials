package com.baeldung.javapoet.test.person;

import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Student implements Person {
    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printNameMultipleTimes() {
        List<String> names = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> names.add(name));
        names.forEach(System.out::println);
    }

    public static void sortByLength(List<String> strings) {
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length();
            }
        });
    }
}
