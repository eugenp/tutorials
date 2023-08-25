package com.baeldung.uniquecharcheck;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class UniqueCharChecker {

    public static boolean bruteForceCheck(String str) {
        char[] chars = str.toUpperCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                if(chars[i] == chars[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean sortAndThenCheck(String str) {
        char[] chars = str.toUpperCase().toCharArray();
        Arrays.sort(chars);
        for (int i = 0; i < chars.length - 1; i++) {
            if(chars[i] == chars[i+1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean useSetCheck(String str) {
        char[] chars = str.toUpperCase().toCharArray();
        Set <Character> set = new HashSet <>();
        for (char c: chars) {
            if (!set.add(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean useStreamCheck(String str) {
        boolean isUnique = str.toUpperCase().chars()
                .mapToObj(c -> (char)c)
                .collect(Collectors.toSet())
                .size() == str.length();
        return isUnique;
    }

    public static boolean useStringUtilscheck(String str) {
        for (int i = 0; i < str.length(); i++) {
            String curChar = String.valueOf(str.charAt(i));
            String remainingStr = str.substring(i+1);
            if(StringUtils.containsIgnoreCase(remainingStr, curChar)) {
                return false;
            }
        }
        return true;
    }
}
