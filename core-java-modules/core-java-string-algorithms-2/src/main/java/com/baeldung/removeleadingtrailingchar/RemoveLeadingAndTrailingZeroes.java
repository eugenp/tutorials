package com.baeldung.removeleadingtrailingchar;


import com.google.common.base.CharMatcher;
import org.apache.commons.lang3.StringUtils;

public class RemoveLeadingAndTrailingZeroes {

    public static String removeLeadingZeroesWithStringBuilder(String s) {
        StringBuilder sb = new StringBuilder(s);

        while (sb.length() > 1 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }

    public static String removeTrailingZeroesWithStringBuilder(String s) {
        StringBuilder sb = new StringBuilder(s);

        while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    public static String removeLeadingZeroesWithSubstring(String s) {
        int index = 0;

        for (; index < s.length() - 1; index++) {
            if (s.charAt(index) != '0') {
                break;
            }
        }

        return s.substring(index);
    }

    public static String removeTrailingZeroesWithSubstring(String s) {
        int index = s.length() - 1;

        for (; index > 0; index--) {
            if (s.charAt(index) != '0') {
                break;
            }
        }

        return s.substring(0, index + 1);
    }

    public static String removeLeadingZeroesWithApacheCommonsStripStart(String s) {
        String stripped = StringUtils.stripStart(s, "0");

        if (stripped.isEmpty() && !s.isEmpty()) {
            return "0";
        }

        return stripped;
    }

    public static String removeTrailingZeroesWithApacheCommonsStripEnd(String s) {
        String stripped = StringUtils.stripEnd(s, "0");

        if (stripped.isEmpty() && !s.isEmpty()) {
            return "0";
        }

        return stripped;
    }

    public static String removeLeadingZeroesWithGuavaTrimLeadingFrom(String s) {
        String stripped = CharMatcher.is('0')
            .trimLeadingFrom(s);

        if (stripped.isEmpty() && !s.isEmpty()) {
            return "0";
        }

        return stripped;
    }

    public static String removeTrailingZeroesWithGuavaTrimTrailingFrom(String s) {
        String stripped = CharMatcher.is('0')
            .trimTrailingFrom(s);

        if (stripped.isEmpty() && !s.isEmpty()) {
            return "0";
        }

        return stripped;
    }

    public static String removeLeadingZeroesWithRegex(String s) {
        return s.replaceAll("^0+(?!$)", "");
    }

    public static String removeTrailingZeroesWithRegex(String s) {
        return s.replaceAll("(?!^)0+$", "");
    }
}
