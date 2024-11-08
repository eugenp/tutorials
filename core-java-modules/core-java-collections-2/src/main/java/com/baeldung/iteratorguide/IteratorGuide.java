package com.baeldung.iteratorguide;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class IteratorGuide {
    
    public static void main(String[] args) {
        List<String> items = new ArrayList<>();
        items.add("ONE");
        items.add("TWO");
        items.add("THREE");
        Iterator<String> iter = items.iterator();
        while (iter.hasNext()) {
                String next = iter.next();
                System.out.println(next);
                iter.remove();
        }
        ListIterator<String> listIterator = items.listIterator();
        while(listIterator.hasNext()) {
            String nextWithIndex = items.get(listIterator.nextIndex());
            String next = listIterator.next();
            if( "ONE".equals(next)) {
                listIterator.set("SWAPPED");
            }
        }
        listIterator.add("FOUR");
        while(listIterator.hasPrevious()) {
            String previousWithIndex = items.get(listIterator.previousIndex());
            String previous = listIterator.previous();
            System.out.println(previous);
        }
        listIterator.forEachRemaining(e -> {
            System.out.println(e);
        });
    }
}
