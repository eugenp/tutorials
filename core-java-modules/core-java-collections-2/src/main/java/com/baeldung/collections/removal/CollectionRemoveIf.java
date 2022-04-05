package com.baeldung.collections.removal;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionRemoveIf {

    public static void main(String args[]) {
        Collection<String> names = new ArrayList<>();
        names.add("John");
        names.add("Ana");
        names.add("Mary");
        names.add("Anthony");
        names.add("Mark");

        names.removeIf(e -> e.startsWith("A"));
        System.out.println(String.join(",", names));
    }
}
