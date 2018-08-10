package com.baeldung.primitive;

import java.util.Random;
import java.util.function.Function;

/**
 * This is a class that measures the execution time of comparison operation for
 * the following pairs of the primitive-reference types:
 * 1. int - Integer
 * 2. long - Long
 * 3. double - Double
 * 4. char - Character
 * 5. byte - Byte
 * 6. boolean - Boolean
 * 7. short - Short
 * 8. float - Float
 */
public class PrimitiveVsReferenceRunner {
    private static Random generator = new Random();

    public static void main(String[] args) {
        int n = 50000000;
        String choice = "int";
        if (args != null) {
            if (args.length > 0) {
                choice = args[0];
            }
            if (args.length > 1) {
                n = Integer.valueOf(args[1]);
            }
        }
        double fraction;
        switch (choice) {
            case "long":
                fraction = runner(n, i -> lookupPrimitiveLong(i), i -> lookupWrapperLong(i));
                break;
            case "double":
                fraction = runner(n, i -> lookupPrimitiveDouble(i), i -> lookupWrapperDouble(i));
                break;
            case "float":
                fraction = runner(n, i -> lookupPrimitiveFloat(i), i -> lookupWrapperFloat(i));
                break;
            case "short":
                fraction = runner(n, i -> lookupPrimitiveShort(i), i -> lookupWrapperShort(i));
                break;
            case "boolean":
                fraction = runner(n, i -> lookupPrimitiveBoolean(i), i -> lookupWrapperBoolean(i));
                break;
            case "byte":
                fraction = runner(n, i -> lookupPrimitiveByte(i), i -> lookupWrapperByte(i));
                break;
            case "char":
                fraction = runner(n, i -> lookupPrimitiveChar(i), i -> lookupWrapperChar(i));
                break;
            case "int":
            default:
                fraction = runner(n, i -> lookupPrimitiveInt(i), i -> lookupWrapperInteger(i));
        }
        System.out.println(fraction);
    }


    private static double runner(int n, Function<Integer, Long> primitive, Function<Integer, Long> reference) {
        long t1, t2;
        if (generator.nextBoolean()) {
            t1 = primitive.apply(n);
            t2 = reference.apply(n);
        } else {
            t2 = reference.apply(n);
            t1 = primitive.apply(n);

        }
        if (t1 != 0) {
            return ((double) t2) / ((double) t1);
        } else {
            return -1.0d;
        }
    }


    private static Long lookupPrimitiveFloat(int s) {
        float[] elements = new float[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        float pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupWrapperFloat(int s) {
        Float[] elements = new Float[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1f;
        }
        Float pivot = 2f;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupPrimitiveInt(int s) {
        int[] elements = new int[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        int pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupWrapperInteger(int s) {
        Integer[] elements = new Integer[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        Integer pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupPrimitiveDouble(int s) {
        double[] elements = new double[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        double pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupWrapperDouble(int s) {
        Double[] elements = new Double[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1D;
        }
        Double pivot = 2D;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupPrimitiveShort(int s) {
        short[] elements = new short[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        short pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupWrapperShort(int s) {
        Short[] elements = new Short[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        Short pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupPrimitiveBoolean(int s) {
        boolean[] elements = new boolean[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = true;
        }
        boolean pivot = false;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupWrapperBoolean(int s) {
        Boolean[] elements = new Boolean[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = Boolean.TRUE;
        }
        Boolean pivot = Boolean.FALSE;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupPrimitiveByte(int s) {
        byte[] elements = new byte[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        byte pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupWrapperByte(int s) {
        Byte[] elements = new Byte[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        Byte pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupPrimitiveChar(int s) {
        char[] elements = new char[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 'a';
        }
        char pivot = 'b';
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupWrapperChar(int s) {
        Character[] elements = new Character[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 'a';
        }
        Character pivot = 'b';
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupPrimitiveLong(int s) {
        long[] elements = new long[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1;
        }
        long pivot = 2;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (pivot != elements[index]) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

    private static long lookupWrapperLong(int s) {
        Long[] elements = new Long[s];
        for (int i = 0; i < s - 1; i++) {
            elements[i] = 1L;
        }
        Long pivot = 2L;
        elements[s - 1] = pivot;
        int index = 0;
        long start = System.currentTimeMillis();
        while (!pivot.equals(elements[index])) {
            index++;
        }
        long duration = System.currentTimeMillis() - start;
        assert index == s - 1 : "Wrong index";
        return duration;
    }

}
