package com.baeldung.generics;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @see java.util.Arrays#stream(int[])
 * @see java.util.stream.Collectors#toList()
 * @author zn.wang
 */
public class Generics {

    /**
     * Array -> List
     * definition of a generic method
     * @param a
     * @param <T>
     * @return
     */
    public static <T> List<T> fromArrayToList(T[] a) {
        return Arrays.stream(a).collect(Collectors.toList());
    }

    /**
     * Array -> List
     * definition of a generic method
     * @param a
     * @param mapperFunction
     * @param <T>
     * @param <G>
     * @return
     */
    public static <T, G> List<G> fromArrayToList(T[] a, Function<T, G> mapperFunction) {
        return Arrays.stream(a).map(mapperFunction).collect(Collectors.toList());
    }

    /**
     * example of a generic method that has Number as an upper bound for T
     * @param a
     * @param <T>
     * @return
     */
    public static <T extends Number> List<T> fromArrayToListWithUpperBound(T[] a) {
        return Arrays.stream(a).collect(Collectors.toList());
    }

    /**
     * example of a generic method with a wild card,
     * this method can be used with a list of any subtype of Building.
     * @param buildings
     */
    public static void paintAllBuildings(List<? extends Building> buildings) {
        buildings.forEach(Building::paint);
    }

}