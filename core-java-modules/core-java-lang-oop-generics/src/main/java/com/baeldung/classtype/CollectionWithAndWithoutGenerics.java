package com.baeldung.classtype;

import java.util.ArrayList;
import java.util.List;

public class CollectionWithAndWithoutGenerics {

    public static void main(String[] args) {
        withoutGenerics();
    }

    private static void withoutGenerics() {
        List container = new ArrayList();
        container.add(1);
        container.add("2");
        container.add("string");

        for (int i = 0; i < container.size(); i++) {
            int val = (int) container.get(i);
        }
    }

    public static void withGenerics() {
        List<Integer> container = new ArrayList();
        container.add(1);
        container.add(2);
        container.add(3);

        for (int i = 0; i < container.size(); i++) {
            int val = container.get(i);
        }
    }
}
