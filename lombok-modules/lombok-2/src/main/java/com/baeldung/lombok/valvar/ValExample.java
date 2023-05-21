package com.baeldung.lombok.valvar;

import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ValExample {
    public Class name() {
        val name = "name";
        System.out.println("Name: " + name);
        return name.getClass();
    }

    public Class age() {
        val age = Integer.valueOf(30);
        System.out.println("Age: " + age);
        return age.getClass();
    }

    public Class listOf() {
        val agenda = new ArrayList<String>();
        agenda.add("Day 1");
        System.out.println("Agenda: " + agenda);
        return agenda.getClass();
    }

    public Class mapOf() {
        val books = new HashMap<Integer, String>();
        books.put(1, "Book 1");
        books.put(2, "Book 2");
        System.out.println("Books:");
        for (val entry : books.entrySet()) {
            System.out.printf("- %d. %s\n", entry.getKey(), entry.getValue());
        }
        return books.getClass();
    }

    public Class compoundTypes(boolean isArray) {
        val compound = isArray ? new ArrayList<String>() : new HashSet<String>();
        return compound.getClass();
    }
}
