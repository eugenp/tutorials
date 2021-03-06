package com.baeldung.reflection.access.privatemethods;

public class LongArrayUtil {

    public static int indexOf(long[] array, long target) {
        return indexOf(array, target, 0, array.length);
    }

    private static int indexOf(long[] array, long target, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

}
