package com.baeldung.replaceremove;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringReplaceAndRemoveUnitTest {

    private static final String INPUT = "some prefix=Important Info: a=b";

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
        String result = INPUT.substring(INPUT.indexOf("=") + 1);
        assertEquals("Important Info: a=b", result);
    }

    @Test
    public void givenTestStrings_whenUsingSplit_thenGetExpectedResult() {
        String result = INPUT.split("=", 2)[1];
        assertEquals("Important Info: a=b", result);
    }

    @Test
    public void givenTestStrings_whenUsingRegexReplaceAll_thenGetExpectedResult() {
        String wrongResult = INPUT.replaceFirst(".*=", "");
        assertEquals("b", wrongResult);

        String result = INPUT.replaceFirst(".*?=", "");
        assertEquals("Important Info: a=b", result);

        result = INPUT.replaceFirst("[^=]*=", "");
        assertEquals("Important Info: a=b", result);
    }

}