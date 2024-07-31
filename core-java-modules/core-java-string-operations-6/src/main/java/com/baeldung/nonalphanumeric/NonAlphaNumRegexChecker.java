package com.baeldung.nonalphanumeric;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NonAlphaNumRegexChecker {

    private static final Pattern PATTERN_NON_ALPHNUM_ANYLANG = Pattern.compile("[^\\p{IsAlphabetic}\\p{IsDigit}]");
    private static final Pattern PATTERN_NON_ALPHNUM_USASCII = Pattern.compile("[^a-zA-Z0-9]+");
    /**
     * checks if a non-alphanumeric character is present. this method would return true if
     * it comes across a non english or non US-ASCII letter
     *
     * @param str - String to check for special character
     * @return true if special character found else false
     */
    public static boolean isNonAlphanumeric(String str) {
//      Pattern pattern = Pattern.compile("\\W");  //same as [^a-zA-Z0-9]+
//      Pattern pattern = Pattern.compile("[^a-zA-Z0-9||\\s]+"); //ignores space
        Matcher matcher = PATTERN_NON_ALPHNUM_USASCII.matcher(str);
        return matcher.find();
    }

    /**
     * Checks for non-alphanumeric characters from all language scripts
     *
     * @param input - String to check for special character
     * @return true if special character found else false
     */
    public static boolean containsNonAlphanumeric(String input) {
//      Pattern pattern = Pattern.compile("[^\\p{Alnum}]", Pattern.UNICODE_CHARACTER_CLASS); //Binary properties
        Matcher matcher = PATTERN_NON_ALPHNUM_ANYLANG.matcher(input);
        return matcher.find();
    }

    /**
     * checks for non-alphanumeric character. it returns true if it detects any character other than the
     * specified script argument. example of script - Character.UnicodeScript.GEORGIAN.name()
     *
     * @param input - String to check for special character
     * @param script - language script
     * @return true if special character found else false
     */
    public static boolean containsNonAlphanumeric(String input, String script) {
        String regexScriptClass = "\\p{" + "Is" + script + "}";
        Pattern pattern = Pattern.compile("[^" + regexScriptClass + "\\p{IsDigit}]"); //Binary properties
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
