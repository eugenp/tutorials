package com.baeldung.array;

public class MaxSizeArray {

    public static void main(String... strings) {
        for (int i = 2; i >= 0; i--) {
            try {
                int[] arr = new int[Integer.MAX_VALUE - i];
                System.out.format("Max-Size", Integer.MAX_VALUE - i);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
