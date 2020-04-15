package com.baeldung.java.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RemoveFromList {

        public static void main(String[] args) {
                List<String> sports = new ArrayList<>();
                sports.add("Football");
                sports.add("Basketball");
                sports.add("Baseball");
                sports.add("Boxing");
                sports.add("Cycling");

                System.out.println("List before removing:  " + sports);

                // Remove with index
                sports.remove(1);

                // Remove with an element
                sports.remove("Baseball");

                // Iterator remove method
                Iterator<String> iterator = sports.iterator();
                while (iterator.hasNext()) {
                        if (iterator.next().equals("Boxing")) {
                                iterator.remove();
                                break;
                        }
                }

                // ArrayList removeIf method (Java 8)
                sports.removeIf(p -> p.equals("Cycling"));

                System.out.println("List after removing:  " + sports);
        }

}
