package com.baeldung.typeinference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeInference {

    // Without type inference. code is verbose.
    Map<String, Map<String, String>> mapOfMaps = new HashMap<String, Map<String, String>>();
    List<String> strList = Collections.<String> emptyList();
    List<Integer> intList = Collections.<Integer> emptyList();

    // With type inference. code is concise.
    List<String> strListInferred = Collections.emptyList();
    List<Integer> intListInferred = Collections.emptyList();

    // Type Inference for constructor using diamond operator.
    Map<String, Map<String, String>> mapOfMapsInferred = new HashMap<>();

    // Generalized target-type inference
    List<String> strListGeneralized = add(new ArrayList<>(), "abc", "def");
    List<Integer> intListGeneralized = add(new ArrayList<>(), 1, 2);
    List<Number> numListGeneralized = add(new ArrayList<>(), 1, 2.0);

    static <T> List<T> add(List<T> list, T a, T b) {
        list.add(a);
        return list;
    }

    public static void main(String[] args) {
        // Type Inference and Lambda Expressions.
        List<Integer> intList = Arrays.asList(5, 2, 4, 2, 1);
        Collections.sort(intList, (a, b) -> a.compareTo(b));
        System.out.println(Arrays.toString(intList.toArray()));

        List<String> strList = Arrays.asList("Red", "Blue", "Green");
        Collections.sort(strList, (a, b) -> a.compareTo(b));
        System.out.println(Arrays.toString(strList.toArray()));
    }
}
