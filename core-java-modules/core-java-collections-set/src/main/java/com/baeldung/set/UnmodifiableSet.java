package com.baeldung.set;

import com.google.common.collect.ImmutableSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UnmodifiableSet {

    public static void main(String[] args) {

        Set<String> set = new HashSet<>();
        set.add("Canada");
        set.add("USA");

        coreJDK(set);
        guavaOf();
        copyOf(set);
        java9Of();
    }

    private static void java9Of() {
        Set<String> immutable = Set.of("Canada", "USA");
        System.out.println(immutable);
    }

    private static void guavaOf() {
        Set<String> immutable = ImmutableSet.of("Canada", "USA");
        System.out.println(immutable);
    }

    private static void copyOf(Set<String> set) {
        Set<String> immutable = ImmutableSet.copyOf(set);
        set.add("Costa Rica");
        System.out.println(immutable);
    }

    private static void coreJDK(Set<String> set) {
        Set<String> unmodifiableSet = Collections.unmodifiableSet(set);
        set.add("Costa Rica");
        System.out.println(unmodifiableSet);
    }
}
