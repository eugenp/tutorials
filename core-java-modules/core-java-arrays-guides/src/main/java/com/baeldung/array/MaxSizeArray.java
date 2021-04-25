package com.baeldung.array;

public class MaxSizeArray {

    public static void main(String... strings) {
        for (int i = 2; i >= 0; i--) {
            try {
                int[] arr = new int[Integer.MAX_VALUE - i];
                System.out.println("Max-Size : "+ arr.length);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
