package com.baeldung.java.countingChars;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.common.base.CharMatcher;

/***
 * Example of counting chars in a String.
 */
public class CountCharsExampleUnitTest {

    @Test
    public void givenString_whenUsingLoop_thenCountChars() {
        String someString = "elephant";
        char someChar = 'e';
        int count = 0;
        for (int i = 0; i < someString.length(); i++) {
            if (someString.charAt(i) == someChar) {
                count++;
            }
        }
        assertEquals(2, count);
    }

    @Test
    public void givenString_whenUsingReplace_thenCountChars() {
        String someString = "elephant";
        int count = someString.length() - someString.replace("e", "").length();
        assertEquals(2, count);
    }

    @Test
    public void givenString_whenUsingSplit_thenCountChars() {
        String someString = "elephant";
        int count = someString.split("e", -1).length - 1;
        assertEquals(2, count);
    }

    @Test
    public void givenString_whenUsingReqExp_thenCountChars() {
        Pattern pattern = Pattern.compile("[^e]*e");
        Matcher matcher = pattern.matcher("elephant");
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void givenString_whenUsingRecursion_thenCountChars() {
        int count = useRecursion("elephant", 'e', 0);
        assertEquals(2, count);
    }

    private int useRecursion(String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + useRecursion(someString, searchedChar, index + 1);
    }

    @Test
    public void givenString_whenUsingStringUtils_thenCountChars() throws InterruptedException {
        int count = StringUtils.countMatches("elephant", "e");
        assertEquals(2, count);
    }

    @Test
    public void givenString_whenUsingJava8Features_thenCountChars() {
        String someString = "elephant";
        long count = someString.chars()
            .filter(ch -> ch == 'e')
            .count();
        assertEquals(2, count);

        long count2 = someString.codePoints()
            .filter(ch -> ch == 'e')
            .count();
        assertEquals(2, count2);
    }

    @Test
    public void givenString_whenUsingGuavaCharMatcher_thenCountChars() {
        int count = CharMatcher.is('e')
            .countIn("elephant");
        assertEquals(2, count);
    }

}
