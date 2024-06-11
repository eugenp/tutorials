package com.baeldung.passclassasparameter;

import java.util.ArrayList;
import java.util.List;

public class GenericExample {
    public static <T> void printListElements(Class<T> clazz, List<T> list) {
        System.out.println("Elements of " + clazz.getSimpleName() + " list:");
        for (T element : list) {
            System.out.println(element);
        }
    }

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("Java");
        stringList.add("is");
        stringList.add("awesome");

        printListElements(String.class, stringList);
    }
}
