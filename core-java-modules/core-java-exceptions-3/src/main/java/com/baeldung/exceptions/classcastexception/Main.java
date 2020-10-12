package com.baeldung.exceptions.classcastexception;

import java.io.Serializable;

public class Main {

    public static void main(String[] args) {

        checkedCasts();
        uncheckedConversion();
        genericConversion();
    }

    private static void checkedCasts() {

        Animal animal = new Frog();

        try {
            Mammal mammal = (Mammal) animal;
        } catch (ClassCastException e) {
            System.out.println("A checked downcast to Mammal is incompatible from Frog because Frog is not a subtype of Mammal.");
        }

        try {
            Serializable serial = (Serializable) animal;
        } catch (ClassCastException e) {
            System.out.println("A checked cast to Serializable is incompatible from Frog because Frog is not a subtype of Serializable.");
        }

        Object primitives = new int[1];

        try {
            Integer[] integers = (Integer[]) primitives;
        } catch (ClassCastException e) {
            System.out.println("A checked cast to Integer[] is incompatible from primitive arrays. Auto-boxing does not work for arrays.");
        }

        try {
            long[] longs = (long[]) primitives;
        } catch (ClassCastException e) {
            System.out.println("A checked cast to long[] is incompatible from int[]. Type promotion does not work for arrays.");
        }
    }

    private static void uncheckedConversion() {
        Box<Long> originalBox = new Box<>();
        Box raw = originalBox;
        raw.setContent(2.5);
        Box<Long> bound = (Box<Long>) raw;
        try {
            Long content = bound.getContent();
        } catch (ClassCastException e) {
            System.out.println("An incompatible element was found in the raw box.");
        }
    }

    private static void genericConversion() {
        try {
            String shouldBeNull = convertInstanceOfObject(123);
        } catch (ClassCastException e) {
            System.out.println("Should have been null, but due to type erasure, inside convertInstanceOfObject, " +
                    "it will attempt to cast to Object instead of String, so it casts to Object, which is always possible.");
        }
    }

    public static <T> T convertInstanceOfObject(Object o) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public interface Animal {
    }

    public static class Reptile implements Animal {
    }

    public static class Frog extends Reptile {
    }

    public static class Mammal implements Animal {
    }

    public static class Box<T> {
        private T content;

        public T getContent() {
            return content;
        }

        public void setContent(T content) {
            this.content = content;
        }
    }
}
