package com.baeldung.objectarray;

public class ObjectArray {

    public static Object[] createSampleArray() {
        Object[] values = new Object[5];
        values[0] = "Hello";                  // String
        values[1] = 42;                       // Autoboxed Integer
        values[2] = 3.14;                     // Autoboxed Double
        values[3] = new int[]{1, 2, 3};      // int[] array
        values[4] = new Person("Alice", 30); // Custom class
        return values;
    }

    // Nested static Person class
    public static class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
