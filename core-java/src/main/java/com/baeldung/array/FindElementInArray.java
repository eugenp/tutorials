package com.baeldung.array;

import java.util.Arrays;
import java.util.function.IntPredicate;

/**
 * @author zn.wang
 */
public class FindElementInArray {

    /**
     * 采用for循环+break的方法
     * @param array
     * @param element
     * @return
     */
    public static boolean findGivenElementInArrayWithoutUsingStream(int[] array, int element) {
        boolean actualResult = false;

        for (int index = 0; index < array.length; index++) {
            if (element == array[index]) {
                actualResult = true;
                break;
            }
        }
        return actualResult;
    }

    /**
     * 采用{@link java.util.Arrays#stream(int[] array)}
     * 和 {@link java.util.stream.IntStream#filter(IntPredicate)}
     * 和 {@link java.util.stream.IntStream#findFirst()}
     * @param array
     * @param element
     * @return
     */
    public static boolean findGivenElementInArrayUsingStream(int[] array, int element) {
        return Arrays.stream(array).filter(x -> element == x).findFirst().isPresent();
    }
}
