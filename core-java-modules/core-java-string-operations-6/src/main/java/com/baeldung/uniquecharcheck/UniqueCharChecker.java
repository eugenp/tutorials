package com.baeldung.uniquecharcheck;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class UniqueCharChecker {

    public static boolean checkV1(String str) {
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

    public static boolean checkV2(String str) {
        char[] chars = str.toUpperCase().toCharArray();
        Arrays.sort(chars);
        for (int i = 0; i < chars.length - 1; i++) {
            if(chars[i] == chars[i+1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkV3(String str) {
        char[] chars = str.toUpperCase().toCharArray();
        Set <Character> set = new HashSet <>();
        for (char c: chars) {
            set.add(c);
        }
        return set.size() == str.length();
    }

    public static boolean checkV4(String str) {
        boolean isUnique = str.toUpperCase().chars()
                .mapToObj(c -> (char)c)
                .collect(Collectors.toSet())
                .size() == str.length();
        return isUnique;
    }

    public static boolean checkV5(String str) {
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
