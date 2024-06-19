package com.baeldung.algorithms.permutation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StringPermutation {

    public static boolean isPermutationWithSorting(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        char[] s1CharArray = s1.toCharArray();
        char[] s2CharArray = s2.toCharArray();
        Arrays.sort(s1CharArray);
        Arrays.sort(s2CharArray);
        return Arrays.equals(s1CharArray, s2CharArray);
    }

    public static boolean isPermutationWithOneCounter(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        int[] counter = new int[256];
        for (int i = 0; i < s1.length(); i++) {
            counter[s1.charAt(i)]++;
            counter[s2.charAt(i)]--;
        }
        for (int count : counter) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPermutationWithTwoCounters(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        int[] counter1 = new int[256];
        int[] counter2 = new int[256];
        for (int i = 0; i < s1.length(); i++) {
            counter1[s1.charAt(i)]++;
        }

        for (int i = 0; i < s2.length(); i++) {
            counter2[s2.charAt(i)]++;
        }

        return Arrays.equals(counter1, counter2);
    }

    public static boolean isPermutationWithMap(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        Map<Character, Integer> charsMap = new HashMap<>();

        for (int i = 0; i < s1.length(); i++) {
            charsMap.merge(s1.charAt(i), 1, Integer::sum);
        }

        for (int i = 0; i < s2.length(); i++) {
            if (!charsMap.containsKey(s2.charAt(i)) || charsMap.get(s2.charAt(i)) == 0) {
                return false;
            }
            charsMap.merge(s2.charAt(i), -1, Integer::sum);
        }

        return true;
    }

    public static boolean isPermutationInclusion(String s1, String s2) {
        int ns1 = s1.length(), ns2 = s2.length();
        if (ns1 < ns2) {
            return false;
        }

        int[] s1Count = new int[26];
        int[] s2Count = new int[26];

        for (char ch : s2.toCharArray()) {
            s2Count[ch - 'a']++;
        }

        for (int i = 0; i < ns1; ++i) {
            s1Count[s1.charAt(i) - 'a']++;
            if (i >= ns2) {
                s1Count[s1.charAt(i - ns2) - 'a']--;
            }
            if (Arrays.equals(s2Count, s1Count)) {
                return true;
            }
        }

        return false;
    }
}
