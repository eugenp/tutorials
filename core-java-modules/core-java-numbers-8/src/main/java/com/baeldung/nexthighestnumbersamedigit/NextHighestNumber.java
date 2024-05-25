package com.baeldung.nexthighestnumbersamedigit;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class NextHighestNumber {

    private static void findPermutations(int num, int index, StringBuilder sb, Set<Integer> hs) {
        if (index == sb.length() - 1) {
            hs.add(Integer.parseInt(sb.toString()));
            return;
        }

        for (int i = index; i < sb.length(); i++) {
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(index));
            sb.setCharAt(index, temp);
            findPermutations(num, index + 1, sb, hs);
            temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(index));
            sb.setCharAt(index, temp); // Swap back after recursion
        }
    }

    public static int usingPermutation(int num) {
        Set<Integer> hs = new TreeSet<>();
        StringBuilder sb = new StringBuilder(String.valueOf(num));
        findPermutations(num, 0, sb, hs);

        for (int n : hs) {
            if (n > num) {
                return n;
            }
        }
        return -1;
    }

    public static int usingSorting(int num) {
        String numStr = String.valueOf(num);
        char[] numChars = numStr.toCharArray();
        int pivotIndex;

        for (pivotIndex = numChars.length - 1; pivotIndex > 0; pivotIndex--) {
            if (numChars[pivotIndex] > numChars[pivotIndex - 1]) {
                break;
            }
        }

        if (pivotIndex == 0) {
            return -1;
        }

        int pivot = numChars[pivotIndex - 1];
        int minIndex = pivotIndex;
        for (int j = pivotIndex + 1; j < numChars.length; j++) {
            if (numChars[j] > pivot && numChars[j] < numChars[minIndex]) {
                minIndex = j;
            }
        }
        // Swap the pivot with the smallest digit found
        swap(numChars, pivotIndex - 1, minIndex);

        // Sort the digits to the right of the pivot in ascending order
        Arrays.sort(numChars, pivotIndex, numChars.length);
        return Integer.parseInt(new String(numChars));
    }

    public static int usingTwoPointer(int num) {
        char[] numChars = Integer.toString(num)
            .toCharArray();
        int pivotIndex = numChars.length - 2;
        while (pivotIndex >= 0 && numChars[pivotIndex] >= numChars[pivotIndex + 1]) {
            pivotIndex--;
        }

        if (pivotIndex == -1)
            return -1;

        int minIndex = numChars.length - 1;
        while (numChars[minIndex] <= numChars[pivotIndex]) {
            minIndex--;
        }
        swap(numChars, pivotIndex, minIndex);
        reverse(numChars, pivotIndex + 1, numChars.length - 1);
        return Integer.parseInt(new String(numChars));
    }

    private static void swap(char[] numChars, int i, int j) {
        char temp = numChars[i];
        numChars[i] = numChars[j];
        numChars[j] = temp;
    }

    private static void reverse(char[] numChars, int i, int j) {
        while (i < j) {
            swap(numChars, i, j);
            i++;
            j--;
        }
    }

    public static void main(String[] args) {
        int result = usingTwoPointer(536479);
        System.out.println(result);
    }
}
