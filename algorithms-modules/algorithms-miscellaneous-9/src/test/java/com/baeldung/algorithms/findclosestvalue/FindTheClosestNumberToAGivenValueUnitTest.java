package com.baeldung.algorithms.findclosestvalue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FindTheClosestNumberToAGivenValueUnitTest {

    private static final List<Integer> UNSORTED_LIST = List.of(8, 100, 6, 89, -23, 77);
    private static final List<Integer> SORTED_LIST = List.of(-23, 6, 8, 77, 89, 100);

    public static int findClosestByLoop(List<Integer> numbers, int target) {
        int closest = numbers.get(0);
        int minDistance = Math.abs(target - closest);
        for (int num : numbers) {
            int distance = Math.abs(target - num);
            if (distance < minDistance) {
                closest = num;
                minDistance = distance;
            }
        }
        return closest;
    }

    @Test
    void givenUnsortedList_whenFindClosestByLoop_thenCorrect() {
        assertEquals(-23, findClosestByLoop(UNSORTED_LIST, -100));
        assertEquals(100, findClosestByLoop(UNSORTED_LIST, 500));
        assertEquals(89, findClosestByLoop(UNSORTED_LIST, 89));
        assertEquals(77, findClosestByLoop(UNSORTED_LIST, 70));
        assertEquals(8, findClosestByLoop(UNSORTED_LIST, 7));
    }

    public static int findClosestByStream(List<Integer> numbers, int target) {
        return numbers.stream()
            .min(Comparator.comparingInt(o -> Math.abs(o - target)))
            .get();
    }

    @Test
    void givenUnsortedList_whenFindClosestByStream_thenCorrect() {
        assertEquals(-23, findClosestByStream(UNSORTED_LIST, -100));
        assertEquals(100, findClosestByStream(UNSORTED_LIST, 500));
        assertEquals(89, findClosestByStream(UNSORTED_LIST, 89));
        assertEquals(77, findClosestByStream(UNSORTED_LIST, 70));
        assertEquals(8, findClosestByStream(UNSORTED_LIST, 7));
    }

    public static int findClosestByBiSearch(List<Integer> sortedNumbers, int target) {
        int first = sortedNumbers.get(0);
        if (target <= first) {
            return first;
        }

        int last = sortedNumbers.get(sortedNumbers.size() - 1);
        if (target >= last) {
            return last;
        }

        int pos = Collections.binarySearch(sortedNumbers, target);
        if (pos > 0) {
            return sortedNumbers.get(pos);
        }
        int insertPos = -(pos + 1);
        int pre = sortedNumbers.get(insertPos - 1);
        int after = sortedNumbers.get(insertPos);

        return Math.abs(pre - target) <= Math.abs(after - target) ? pre : after;
    }

    @Test
    void givenSortedList_whenFindTheClosestNumberUsingStream_thenCorrect() {
        assertEquals(-23, findClosestByBiSearch(SORTED_LIST, -100));
        assertEquals(100, findClosestByBiSearch(SORTED_LIST, 500));
        assertEquals(89, findClosestByBiSearch(SORTED_LIST, 89));
        assertEquals(77, findClosestByBiSearch(SORTED_LIST, 70));
        assertEquals(6, findClosestByBiSearch(SORTED_LIST, 7));
    }
}