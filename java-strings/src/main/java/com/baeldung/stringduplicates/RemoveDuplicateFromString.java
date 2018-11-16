package com.baeldung.stringduplicates;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RemoveDuplicateFromString {

    static String removeDuplicatesUsinglinkedHashSet(String str) {

        StringBuilder sb = new StringBuilder();
        Set<Character> linkedHashSet = new LinkedHashSet<>();

        for (int i = 0; i < str.length(); i++)
            linkedHashSet.add(str.charAt(i));

        for (Character c : linkedHashSet)
            sb.append(c);

        return sb.toString();
    }

    static String removeDuplicatesUsingSorting(String str) {

        char[] chars = str.toCharArray();

        Arrays.sort(chars);

        StringBuilder sb = new StringBuilder();
        sb.append(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] != chars[i - 1]) {
                sb.append(chars[i]);
            }
        }

        return sb.toString();
    }

    static String removeDuplicatesUsingHashSet(String str) {

        StringBuilder sb = new StringBuilder();
        Set<Character> hashSet = new HashSet<>();

        for (int i = 0; i < str.length(); i++)
            hashSet.add(str.charAt(i));

        for (Character c : hashSet)
            sb.append(c);

        return sb.toString();
    }

    static String removeDuplicatesUsingIndexOf(String str) {

        StringBuilder sb = new StringBuilder();
        int idx;
        for (int i = 0; i < str.length(); i++) {
            idx = str.indexOf(str.charAt(i), i + 1);
            if (idx == -1) {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println("Using LinkedHashSet: " + removeDuplicatesUsinglinkedHashSet("there are some moose on the loose and geeks for geeks"));
        System.out.println("Using Sorting: " + removeDuplicatesUsingSorting("there are some moose on the loose and geeks for geeks"));
        System.out.println("Using HashSet: " + removeDuplicatesUsingHashSet("there are some moose on the loose and geeks for geeks"));
        System.out.println("Using IndexOf: " + removeDuplicatesUsingIndexOf("there are some moose on the loose and geeks for geeks"));
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Using LinkedHashSet: " + removeDuplicatesUsinglinkedHashSet("racecar"));
        System.out.println("Using Sorting: " + removeDuplicatesUsingSorting("racecar"));
        System.out.println("Using HashSet: " + removeDuplicatesUsingHashSet("racecar"));
        System.out.println("Using IndexOf: " + removeDuplicatesUsingIndexOf("racecar"));
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Using LinkedHashSet: " + removeDuplicatesUsinglinkedHashSet("programming with j2ee"));
        System.out.println("Using Sorting: " + removeDuplicatesUsingSorting("programming with j2ee"));
        System.out.println("Using HashSet: " + removeDuplicatesUsingHashSet("programming with j2ee"));
        System.out.println("Using IndexOf: " + removeDuplicatesUsingIndexOf("programming with j2ee"));

    }
}


