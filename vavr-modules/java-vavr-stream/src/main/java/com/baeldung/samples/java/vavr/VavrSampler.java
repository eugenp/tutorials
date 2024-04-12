package com.baeldung.samples.java.vavr;

import io.vavr.collection.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author baeldung
 */
public class VavrSampler {

    static int[] intArray = new int[]{1, 2, 4};
    static List<Integer> intList = new ArrayList<Integer>();
    static int[][] intOfInts = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    public static void main(String[] args) {
        vavrStreamElementAccess();
        System.out.println("====================================");
        vavrParallelStreamAccess();
        System.out.println("====================================");
        jdkFlatMapping();
        System.out.println("====================================");
        vavrStreamManipulation();
        System.out.println("====================================");
        vavrStreamDistinct();
    }

    public static void vavrStreamElementAccess() {
        System.out.println("Vavr Element Access");
        System.out.println("====================================");
        Stream<Integer> vavredStream = Stream.ofAll(intArray);
        System.out.println("Vavr index access: " + vavredStream.get(2));
        System.out.println("Vavr head element access: " + vavredStream.get());

        Stream<String> vavredStringStream = Stream.of("foo", "bar", "baz");
        System.out.println("Find foo " + vavredStringStream.indexOf("foo"));
    }

    public static void vavrParallelStreamAccess() {
        try {
            System.out.println("Vavr Stream Concurrent Modification");
            System.out.println("====================================");
            Stream<Integer> vavrStream = Stream.ofAll(intList);
            intList.add(5);
            vavrStream.forEach(i -> System.out.println("in a Vavr Stream: " + i));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Stream<Integer> wrapped = Stream.ofAll(intArray);
        intArray[2] = 5;
        wrapped.forEach(i -> System.out.println("Vavr looped " + i));
    }

    public static void jdkFlatMapping() {
        System.out.println("JDK FlatMap -> Uncomment line 68 to test");
        System.out.println("====================================");
        int[][] intOfInts = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        IntStream mapToInt = Arrays.stream(intOfInts)
                .map(intArr -> Arrays.stream(intArr))
                .flatMapToInt(val -> val.map(n -> {
            return n * n;
        }))
                .peek(n -> System.out.println("Peeking at " + n));
        //Uncomment to execute pipeline
        //mapToInt.forEach(n -> System.out.println("FlatMapped Result "+n));
    }

    public static void vavrStreamManipulation() {
        System.out.println("Vavr Stream Manipulation");
        System.out.println("====================================");
        List<String> stringList = new ArrayList<>();
        stringList.add("foo");
        stringList.add("bar");
        stringList.add("baz");
        Stream<String> vavredStream = Stream.ofAll(stringList);
        vavredStream.forEach(item -> System.out.println("Vavr Stream item: " + item));
        Stream<String> vavredStream2 = vavredStream.insert(2, "buzz");
        vavredStream2.forEach(item -> System.out.println("Vavr Stream item after stream addition: " + item));
        stringList.forEach(item -> System.out.println("List item after stream addition: " + item));
        Stream<String> deletionStream = vavredStream.remove("bar");
        deletionStream.forEach(item -> System.out.println("Vavr Stream item after stream item deletion: " + item));

    }

    public static void vavrStreamDistinct() {
        Stream<String> vavredStream = Stream.of("foo", "bar", "baz", "buxx", "bar", "bar", "foo");
        Stream<String> distinctVavrStream = vavredStream.distinctBy((y, z) -> {
            return y.compareTo(z);
        });
        distinctVavrStream.forEach(item -> System.out.println("Vavr Stream item after distinct query " + item));

    }
}
