package com.baeldung.algorithms.stringsort;

import java.util.Arrays;
import java.util.Comparator;

public class AlphanumericSort {

    public static String lexicographicSort(String input) {
        char[] stringChars = input.toCharArray();
        Arrays.sort(stringChars);
        return new String(stringChars);
    }

    public static String[] naturalAlphanumericSort(String[] arrayToSort) {
        Arrays.sort(arrayToSort, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return extractInt(s1) - extractInt(s2);
            }

            private int extractInt(String str) {
                String num = str.replaceAll("\\D+", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
        return arrayToSort;
    }

    public static String[] naturalAlphanumericCaseInsensitiveSort(String[] arrayToSort) {
        Arrays.sort(arrayToSort, Comparator.comparing((String s) -> s.replaceAll("\\d", "").toLowerCase())
            .thenComparingInt(s -> {
                String num = s.replaceAll("\\D+", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            })
            .thenComparing(Comparator.naturalOrder()));
        return arrayToSort;
    }
}