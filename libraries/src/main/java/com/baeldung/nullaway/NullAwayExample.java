package com.baeldung.nullaway;

import com.baeldung.distinct.Person;

import javax.annotation.Nullable;

public class NullAwayExample {

    static void printAge(@Nullable Person person) {
        System.out.println(person.getAge());
    }

}