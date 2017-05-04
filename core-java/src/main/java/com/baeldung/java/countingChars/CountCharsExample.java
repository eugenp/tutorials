package com.baeldung.java.countingChars;

import com.google.common.base.CharMatcher;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Example of counting chars in a String.
 */
public class CountCharsExample {

    public static void main(String... args) throws InterruptedException {
        useLoopToCountChars();
        useReplaceToCountChars();
        useSplitToCountChars();
        useStringUtilsToCountChars();
        useRecursionToCountChars("eeelephant", 'e', 0);
        useReqExpToCountChars();
        useJava8FeaturesToCountChars();
        useGuavaCharMatcherToCountChars();
    }

    private static void useLoopToCountChars() {
        String someString = "elephant";
        char someChar = 'e';
        int count = 0;
        for (int i = 0; i < someString.length(); i++) {
            if (someString.charAt(i) == someChar) {
                count++;
            }
        }
        System.out.println("In the string 'elephant', char 'e' occurred " + count + " times.");
    }

    private static void useReplaceToCountChars() {
        String someString = "elephant";
        int count = someString.length() - someString.replace("e", "").length();
        System.out.println("In the string 'elephant', char 'e' occurred " + count + " times.");
    }

    private static void useSplitToCountChars() {
        String someString = "elephant";
        int count = someString.split("e", -1).length - 1;
        System.out.println("In the string 'elephant', char 'e' occurred " + count + " times.");
    }

    private static void useReqExpToCountChars() {
        Pattern pattern = Pattern.compile("[^e]*e");
        Matcher matcher = pattern.matcher("elephant");
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        System.out.println("In the string 'elephant', char 'e' occurred " + count + " times.");
    }

    private static int useRecursionToCountChars(String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + useRecursionToCountChars(someString, searchedChar, index + 1);
    }

    private static void useStringUtilsToCountChars() throws InterruptedException {
        int count = StringUtils.countMatches("elephant", "e");
        System.out.println("In the string 'elephant', char 'e' occurred " + count + " times.");
    }

    private static void useJava8FeaturesToCountChars() {
        String someString = "elephant";
        long count = someString.chars().filter(ch -> ch == 'e').count();
        System.out.println("In the string 'elephant', char 'e' occurred " + count + " times.");

        long count2 = someString.codePoints().filter(ch -> ch == 'e').count();
        System.out.println("In the string 'elephant', char 'e' occurred " + count2 + " times.");
    }

    private static void useGuavaCharMatcherToCountChars() {
        int count = CharMatcher.is('e').countIn("elephant");
        System.out.println("In the string 'elephant', char 'e' occurred " + count + " times.");
    }

}
