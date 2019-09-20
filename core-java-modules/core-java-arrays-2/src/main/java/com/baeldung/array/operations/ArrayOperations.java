package com.baeldung.array.operations;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ArrayOperations {

    // Get the first and last item of an array
    public static <T> T getFirstObject(T[] array) {
        return array[0];
    }

    public static int getFirstInt(int[] array) {
        return array[0];
    }

    public static <T> T getLastObject(T[] array) {
        return array[array.length - 1];
    }

    public static int getLastInt(int[] array) {
        return array[array.length - 1];
    }

    // Append a new item to an array
    public static <T> T[] appendObject(T[] array, T newItem) {
        return ArrayUtils.add(array, newItem);
    }

    public static int[] appendInt(int[] array, int newItem) {
        int[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[newArray.length - 1] = newItem;
        return newArray;
    }

    public static int[] appendIntWithUtils(int[] array, int newItem) {
        return ArrayUtils.add(array, newItem);
    }

    // Compare two arrays to check if they have the same elements
    public static <T> boolean compareObjectArrays(T[] array1, T[] array2) {
        return Arrays.equals(array1, array2);
    }

    public static boolean compareIntArrays(int[] array1, int[] array2) {
        return Arrays.equals(array1, array2);
    }

    // Deep Compare (for nested arrays)
    public static <T> boolean deepCompareObjectArrayUsingArrays(T[][] array1, T[][] array2) {
        // We can use Objects.deepEquals for a broader approach
        return Arrays.deepEquals(array1, array2);
    }

    public static boolean deepCompareIntArrayUsingArrays(int[][] array1, int[][] array2) {
        return Arrays.deepEquals(array1, array2);
    }

    // Check if array is empty
    public static <T> boolean isEmptyObjectArrayUsingUtils(T[] array1) {
        return ArrayUtils.isEmpty(array1);
    }

    public static boolean isEmptyIntArrayUsingUtils(int[] array1) {
        return ArrayUtils.isEmpty(array1);
    }

    // Remove duplicates
    public static Integer[] removeDuplicateObjects(Integer[] array) {
        // We can use other ways of converting the array to a set too
        Set<Integer> set = new HashSet<>(Arrays.asList(array));
        return set.toArray(new Integer[set.size()]);
    }

    public static int[] removeDuplicateInts(int[] array) {
        // Box
        Integer[] list = ArrayUtils.toObject(array);
        // Remove duplicates
        Set<Integer> set = new HashSet<Integer>(Arrays.asList(list));
        // Create array and unbox
        return ArrayUtils.toPrimitive(set.toArray(new Integer[set.size()]));
    }

    // Remove duplicates preserving the order
    public static Integer[] removeDuplicateWithOrderObjectArray(Integer[] array) {
        // We can use other ways of converting the array to a set too
        Set<Integer> set = new LinkedHashSet<>(Arrays.asList(array));
        return set.toArray(new Integer[set.size()]);
    }

    public static int[] removeDuplicateWithOrderIntArray(int[] array) {
        // Box
        Integer[] list = ArrayUtils.toObject(array);
        // Remove duplicates
        Set<Integer> set = new LinkedHashSet<Integer>(Arrays.asList(list));
        // Create array and unbox
        return ArrayUtils.toPrimitive(set.toArray(new Integer[set.size()]));
    }

    // Print an array
    public static String printObjectArray(Integer[] array) {
        return ArrayUtils.toString(array);
    }

    public static String printObjectArray(Integer[][] array) {
        return ArrayUtils.toString(array);
    }

    public static String printIntArray(int[] array) {
        return ArrayUtils.toString(array);
    }

    public static String printIntArray(int[][] array) {
        return ArrayUtils.toString(array);
    }

    // Box or Unbox values
    public static int[] unboxIntegerArray(Integer[] array) {
        return ArrayUtils.toPrimitive(array);
    }

    public static Integer[] boxIntArray(int[] array) {
        return ArrayUtils.toObject(array);
    }

    // Map array values
    @SuppressWarnings("unchecked")
    public static <T, U> U[] mapObjectArray(T[] array, Function<T, U> function, Class<U> targetClazz) {
        U[] newArray = (U[]) Array.newInstance(targetClazz, array.length);
        for (int i = 0; i < array.length; i++) {
            newArray[i] = function.apply(array[i]);
        }
        return newArray;
    }

    public static String[] mapIntArrayToString(int[] array) {
        return Arrays.stream(array)
            .mapToObj(value -> String.format("Value: %s", value))
            .toArray(String[]::new);
    }

    // Filter array values
    public static Integer[] filterObjectArray(Integer[] array, Predicate<Integer> predicate) {
        return Arrays.stream(array)
            .filter(predicate)
            .toArray(Integer[]::new);
    }

    public static int[] filterIntArray(int[] array, IntPredicate predicate) {
        return Arrays.stream(array)
            .filter(predicate)
            .toArray();
    }

    // Insert item between others
    public static int[] insertBetweenIntArray(int[] array, int... values) {
        return ArrayUtils.insert(2, array, values);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] insertBetweenObjectArray(T[] array, T... values) {
        return ArrayUtils.insert(2, array, values);
    }

    // Shuffling arrays
    public static int[] shuffleIntArray(int[] array) {
        // we create a different array instance for testing purposes
        int[] shuffled = Arrays.copyOf(array, array.length);
        ArrayUtils.shuffle(shuffled);
        return shuffled;
    }

    public static <T> T[] shuffleObjectArray(T[] array) {
        // we create a different array instance for testing purposes
        T[] shuffled = Arrays.copyOf(array, array.length);
        ArrayUtils.shuffle(shuffled);
        return shuffled;
    }

    // Get random number
    public static int getRandomFromIntArray(int[] array) {
        return array[new Random().nextInt(array.length)];
    }

    public static <T> T getRandomFromObjectArray(T[] array) {
        return array[new Random().nextInt(array.length)];
    }

    public static Integer[] intersectionSimple(final Integer[] a, final Integer[] b){
        return Stream.of(a).filter(Arrays.asList(b)::contains).toArray(Integer[]::new);
    }

    public static Integer[] intersectionSet(final Integer[] a, final Integer[] b){
        return Stream.of(a).filter(Arrays.asList(b)::contains).distinct().toArray(Integer[]::new);
    }

    public static Integer[] intersectionMultiSet(final Integer[] a, final Integer[] b){
        return Stream.of(a).filter(new LinkedList<>(Arrays.asList(b))::remove).toArray(Integer[]::new);
    }
}
