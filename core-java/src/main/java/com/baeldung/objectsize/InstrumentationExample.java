package com.baeldung.objectsize;

import java.util.ArrayList;
import java.util.List;

public class InstrumentationExample {

    public static void printObjectSize(final Object object) {
        System.out.println("Object type: " + object.getClass() + ", size: " + InstrumentationAgent.getObjectSize(object) + " bytes");
    }

    public static void main(final String[] arguments) {
        final String emptyString = "";
        final String string = "Estimating Object Size Using Instrumentation";
        final String[] stringArray = { emptyString, string, "com.baeldung" };
        final String[] anotherStringArray = new String[100];
        final List<String> stringList = new ArrayList<>();
        final StringBuilder stringBuilder = new StringBuilder(100);
        final int maxIntPrimitive = Integer.MAX_VALUE;
        final int minIntPrimitive = Integer.MIN_VALUE;
        final Integer maxInteger = Integer.MAX_VALUE;
        final Integer minInteger = Integer.MIN_VALUE;
        final long zeroLong = 0L;
        final double zeroDouble = 0.0;
        final boolean falseBoolean = false;
        final Object object = new Object();

        class EmptyClass {
        }
        final EmptyClass emptyClass = new EmptyClass();

        class StringClass {
            public String s;
        }
        final StringClass stringClass = new StringClass();

        printObjectSize(emptyString);
        printObjectSize(string);
        printObjectSize(stringArray);
        printObjectSize(anotherStringArray);
        printObjectSize(stringList);
        printObjectSize(stringBuilder);
        printObjectSize(maxIntPrimitive);
        printObjectSize(minIntPrimitive);
        printObjectSize(maxInteger);
        printObjectSize(minInteger);
        printObjectSize(zeroLong);
        printObjectSize(zeroDouble);
        printObjectSize(falseBoolean);
        printObjectSize(Day.TUESDAY);
        printObjectSize(object);
        printObjectSize(emptyClass);
        printObjectSize(stringClass);
    }

    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
