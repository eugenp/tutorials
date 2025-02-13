package com.baeldung.objectsize;

public class InstrumentedSize {

    public static void main(String[] args) {
        String ds = "Data Structures";
        Course course = new Course(ds);

        System.out.println(ObjectSizeCalculator.sizeOf(course));
    }
}
