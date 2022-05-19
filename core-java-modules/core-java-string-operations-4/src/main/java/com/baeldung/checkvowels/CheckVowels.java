package com.baeldung.checkvowels;

import java.util.regex.Pattern;

public class CheckVowels {
    private static final String VOWELS = "aeiouAEIOU";
    private static final Pattern VOWELS_PATTERN = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE);

    public static boolean isInVowelsString(char c) {
        return VOWELS.indexOf(c) != -1;
    }

    public static boolean isInVowelsString(String c) {
        return VOWELS.contains(c);
    }

    public static boolean isVowelBySwitch(char c) {
        switch (c) {
        case 'a':
        case 'e':
        case 'i':
        case 'o':
        case 'u':
        case 'A':
        case 'E':
        case 'I':
        case 'O':
        case 'U':
            return true;
        default:
            return false;
        }
    }

    public static boolean isVowelByRegex(String c) {
        return VOWELS_PATTERN.matcher(c).matches();
    }
}
