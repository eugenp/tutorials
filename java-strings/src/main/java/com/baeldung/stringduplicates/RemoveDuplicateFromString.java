package com.baeldung.stringduplicates;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RemoveDuplicateFromString {


    String removeDuplicatesUsingCharArray(String str) {

        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        int repeatedCtr;
        for (int i = 0; i < chars.length; i++) {
            repeatedCtr = 0;
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    repeatedCtr++;
                }
            }
            if (repeatedCtr == 0) {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    String removeDuplicatesUsinglinkedHashSet(String str) {

        StringBuilder sb = new StringBuilder();
        Set<Character> linkedHashSet = new LinkedHashSet<>();

        for (int i = 0; i < str.length(); i++) {
            linkedHashSet.add(str.charAt(i));
        }

        for (Character c : linkedHashSet) {
            sb.append(c);
        }

        return sb.toString();
    }

    String removeDuplicatesUsingSorting(String str) {
        StringBuilder sb = new StringBuilder();
        if(!str.isEmpty()) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);

            sb.append(chars[0]);
            for (int i = 1; i < chars.length; i++) {
                if (chars[i] != chars[i - 1]) {
                    sb.append(chars[i]);
                }
            }
        }

        return sb.toString();
    }

    String removeDuplicatesUsingHashSet(String str) {

        StringBuilder sb = new StringBuilder();
        Set<Character> hashSet = new HashSet<>();

        for (int i = 0; i < str.length(); i++) {
            hashSet.add(str.charAt(i));
        }

        for (Character c : hashSet) {
            sb.append(c);
        }

        return sb.toString();
    }

    String removeDuplicatesUsingIndexOf(String str) {

        StringBuilder sb = new StringBuilder();
        int idx;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            idx = str.indexOf(c, i + 1);
            if (idx == -1) {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    String removeDuplicatesUsingDistinct(String str) {
        StringBuilder sb = new StringBuilder();
        str.chars().distinct().forEach(c -> sb.append((char) c));
        return sb.toString();
    }

}


