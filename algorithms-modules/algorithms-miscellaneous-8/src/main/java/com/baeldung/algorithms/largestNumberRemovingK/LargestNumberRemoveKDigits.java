package com.baeldung.algorithms.largestNumberRemovingK;

import java.util.Stack;

public class LargestNumberRemoveKDigits {
    public static int findLargestNumberUsingArithmetic(int num, int k) {
        for (int j = 0; j < k; j++) {

            int result = 0;
            int i = 1;

            while (num / i > 0) {
                int temp = (num / (i * 10))
                        * i
                        + (num % i);
                i *= 10;

                result = Math.max(result, temp);
            }
            num = result;
        }

        return num;
    }

    public static int findLargestNumberUsingStack(int num, int k) {
        String numStr = Integer.toString(num);
        int length = numStr.length();

        if (k == length) return 0;

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < length; i++) {
            char digit = numStr.charAt(i);

            while (k > 0 && !stack.isEmpty() && stack.peek() < digit) {
                stack.pop();
                k--;
            }

            stack.push(digit);
        }

        while (k > 0) {
            stack.pop();
            k--;
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, stack.pop());
        }

        return Integer.parseInt(result.toString());
    }
}
