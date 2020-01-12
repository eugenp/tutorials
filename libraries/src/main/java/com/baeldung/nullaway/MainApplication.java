package com.baeldung.nullaway;

import com.baeldung.distinct.Person;

public class MainApplication {

    static void printAge(Person person) {
        if (person != null) {
            System.out.println(person.getAge());
        }
    }

    static void fooPersonAge() {
        printAge(null);
    }

    public static void main(String[] args) {
        fooPersonAge();
    }

}