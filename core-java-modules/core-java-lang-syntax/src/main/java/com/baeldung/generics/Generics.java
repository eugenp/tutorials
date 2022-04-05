package com.baeldung.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Generics {

    // definition of a generic method
    public static <T> List<T> fromArrayToList(T[] a) {
        return Arrays.stream(a).collect(Collectors.toList());
    }

    // definition of a generic method
    public static <T, G> List<G> fromArrayToList(T[] a, Function<T, G> mapperFunction) {
        return Arrays.stream(a).map(mapperFunction).collect(Collectors.toList());
    }

    // example of a generic method that has Number as an upper bound for T
    public static <T extends Number> List<T> fromArrayToListWithUpperBound(T[] a) {
        return Arrays.stream(a).collect(Collectors.toList());
    }

    // example of a generic method with a wild card, this method can be used
    // with a list of any subtype of Building
    public static void paintAllBuildings(List<? extends Building> buildings) {
        buildings.forEach(Building::paint);
    }

    public static List<Integer> createList(int a) {
        List<Integer> list = new ArrayList<>();
        list.add(a);
        return list;
    }

    public <T> List<T> genericMethod(List<T> list) {
        return list.stream().collect(Collectors.toList());
    }
    
    public List<Object> withErasure(List<Object> list) {
        return list.stream().collect(Collectors.toList());
    }
}