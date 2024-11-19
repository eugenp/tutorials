package com.baeldung.replaceremove;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringReplaceAndRemoveUnitTest {

    private static final String INPUT1 = "some prefix=Important Info: a=b";
    private static final String INPUT2 = "some prefix<a, <b, c>>";
    private static final String INPUT3 = "The first word, the second word, the last word should be replaced.";
    private static final String EXPECTED3 = "The first word, the second word, the last *WORD* should be replaced.";

    @Test
    public void givenTestStrings_whenReplace_thenProcessedString() {

        String master = "Hello World Baeldung!";
        String target = "Baeldung";
        String replacement = "Java";
        String processed = master.replace(target, replacement);
        assertTrue(processed.contains(replacement));
        assertFalse(processed.contains(target));
    }

    @Test
    public void givenTestStrings_whenReplaceAll_thenProcessedString() {

        String master2 = "Welcome to Baeldung, Hello World Baeldung";
        String regexTarget = "(Baeldung)$";
        String replacement = "Java";
        String processed2 = master2.replaceAll(regexTarget, replacement);
        assertTrue(processed2.endsWith("Java"));

    }

    @Test
    public void givenTestStrings_whenStringBuilderMethods_thenProcessedString() {

        String master = "Hello World Baeldung!";
        String target = "Baeldung";
        String replacement = "Java";

        int startIndex = master.indexOf(target);
        int stopIndex = startIndex + target.length();

        StringBuilder builder = new StringBuilder(master);

        builder.delete(startIndex, stopIndex);
        assertFalse(builder.toString()
            .contains(target));

        builder.replace(startIndex, stopIndex, replacement);
        assertTrue(builder.toString()
            .contains(replacement));

    }

    @Test
    public void givenTestStrings_whenStringUtilsMethods_thenProcessedStrings() {

        String master = "Hello World Baeldung!";
        String target = "Baeldung";
        String replacement = "Java";

        String processed = StringUtils.replace(master, target, replacement);
        assertTrue(processed.contains(replacement));

        String master2 = "Hello World Baeldung!";
        String target2 = "baeldung";
        String processed2 = StringUtils.replaceIgnoreCase(master2, target2, replacement);
        assertFalse(processed2.contains(target));

    }

    @Test
    public void givenTestStrings_whenReplaceExactWord_thenProcessedString() {
        String sentence = "A car is not the same as a carriage, and some planes can carry cars inside them!";
        String regexTarget = "\\bcar\\b";
        String exactWordReplaced = sentence.replaceAll(regexTarget, "truck");
        assertEquals("A truck is not the same as a carriage, and some planes can carry cars inside them!", exactWordReplaced);
    }

    @Test
    public void givenTestStrings_whenReplaceExactWordUsingRegExUtilsMethod_thenProcessedString() {
        String sentence = "A car is not the same as a carriage, and some planes can carry cars inside them!";
        String regexTarget = "\\bcar\\b";
        String exactWordReplaced = RegExUtils.replaceAll(sentence, regexTarget, "truck");
        assertEquals("A truck is not the same as a carriage, and some planes can carry cars inside them!", exactWordReplaced);
    }

    @Test
    public void givenTestStrings_whenUsingIndexOfAndSubstring_thenGetExpectedResult() {
        String result1 = INPUT1.substring(INPUT1.indexOf("=") + 1);
        assertEquals("Important Info: a=b", result1);
        String result2 = INPUT2.substring(INPUT2.indexOf("<"));
        assertEquals("<a, <b, c>>", result2);
    }

    @Test
    public void givenTestStrings_whenUsingSplit_thenGetExpectedResult() {
        String result1 = INPUT1.split("=", 2)[1];
        assertEquals("Important Info: a=b", result1);

        String result2 = INPUT2.split("(?=<)", 2)[1];
        assertEquals("<a, <b, c>>", result2);
    }

    @Test
    public void givenTestStrings_whenUsingRegexReplaceAll_thenGetExpectedResult() {
        String wrongResult = INPUT1.replaceFirst(".*=", "");
        assertEquals("b", wrongResult);

        String result1 = INPUT1.replaceFirst(".*?=", "");
        assertEquals("Important Info: a=b", result1);

        result1 = INPUT1.replaceFirst("[^=]*=", "");
        assertEquals("Important Info: a=b", result1);

        String result2 = INPUT2.replaceFirst("[^<]*", "");
        assertEquals("<a, <b, c>>", result2);

    }

    private String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }

    @Test
    public void givenTestStrings_whenReverseAndReplaceFirst_thenGetExpectedResult() {
        String theWord = "word";
        String replacement = "*WORD*";

        String reversedInput = reverseString(INPUT3);
        String reversedTheWord = reverseString(theWord);
        String reversedReplacement = reverseString(replacement);
        String reversedResult = reversedInput.replaceFirst(reversedTheWord, reversedReplacement);

        String result = reverseString(reversedResult);

        assertEquals(EXPECTED3, result);
    }

    @Test
    public void givenTestStrings_whenUsingLastIndexOf_thenGetExpectedResult() {
        String theWord = "word";
        String replacement = "*WORD*";

        int index = INPUT3.lastIndexOf(theWord);
        String result = INPUT3.substring(0, index) + replacement + INPUT3.substring(index + theWord.length());

        assertEquals(EXPECTED3, result);
    }

}