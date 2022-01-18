package com.baeldung.doublequotesremoval;

import com.google.common.base.CharMatcher;

public class DoubleQuotesRemovalUtils {
    
    public static String removeWithSubString(String input) {
        if (input != null && input.length() >= 2 && input.charAt(0) == '\"' 
            && input.charAt(input.length() - 1) == '\"') {
            return input.substring(1, input.length() - 1);
        }

        return input;
    }

    public static String removeWithReplaceAllSimple(String input) {
        if (input == null || input.isEmpty())
            return input;

        return input.replaceAll("\"", "");
    }
    
    public static String removeWithReplaceAllAdvanced(String input) {
        if (input == null || input.isEmpty())
            return input;

        return input.replaceAll("^\"|\"$", "");
    }
    
    public static String removeWithGuava(String input) {
        if (input == null || input.isEmpty())
            return input;
        
        return CharMatcher.is('\"').trimFrom(input);
    }

}
