package com.baeldung.nonalphanumeric;

import org.apache.commons.lang3.StringUtils;

public class NonAlphaNumericChecker {
    /**
     * Checks for non-alphanumeric characters in any Unicode Script
     * @param str - String to check for special characters
     * @return true if special character found else false
     */
    public static boolean isNonAlphanumericAnyLangScript(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks for special characters,returns false if any character
     * found other than the script argument
     * @param str - String to check for special characters
     * @param script - Language script
     * @return true if special character found else false
     */
    public static boolean isNonAlphanumericInLangScript(String str, String script) {

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
//            script e.g., Character.UnicodeScript.of(c).toString().equalsIgnoreCase(Character.UnicodeScript.LATIN.toString())
            if (!Character.UnicodeScript.of(c).toString().equalsIgnoreCase(script)
                    && !Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks for special characters in any lang
     * @param str - String to check for special characters
     * @return true if special character found else false
     */
    public static boolean isNonAlphanumericAnyLangScriptV2(String str) {
        return !StringUtils.isAlphanumeric(str);
    }
}
