package com.baeldung.splitstring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class SplitStringUnitTest {
    private static final String SPACE = " ";
    private static final String TAB = "	";
    private static final String NEW_LINE = "\n";

    private static final String FRUITS_TAB_SEPARATED = "Apple" + TAB + "Banana" + TAB + "Mango" + TAB + "Orange";
    private static final String FRUITS_SPACE_SEPARATED = "Apple" + SPACE + "Banana" + SPACE + "Mango" + SPACE + "Orange";
    private static final String FRUITS_NEWLINE_SEPARATED = "Apple" + NEW_LINE + "Banana" + NEW_LINE + "Mango" + NEW_LINE + "Orange";

    @Test
    public void givenSpaceSeparatedString_whenSplitUsingSpace_shouldGetExpectedResult() {
        String fruits = FRUITS_SPACE_SEPARATED;
        String[] fruitArray = fruits.split(SPACE);
        verifySplit(fruitArray);
    }

    @Test
    public void givenTabSeparatedString_whenSplitUsingTab_shouldGetExpectedResult() {
        String fruits = FRUITS_TAB_SEPARATED;
        String[] fruitArray = fruits.split(TAB);
        verifySplit(fruitArray);
    }

    @Test
    public void givenNewlineSeparatedString_whenSplitUsingNewline_shouldGetExpectedResult() {
        String fruits = FRUITS_NEWLINE_SEPARATED;
        String[] fruitArray = fruits.split(NEW_LINE);
        verifySplit(fruitArray);
    }

    @Test
    public void givenTabSeparatedString_whenSplitUsingSpace_shouldNowSplit() {
        String fruits = FRUITS_TAB_SEPARATED;
        String[] fruitArray = fruits.split(" ");
        assertEquals(1, fruitArray.length);
        assertEquals(fruits, fruitArray[0]);
    }

    @Test
    public void givenWhiteSpaceSeparatedString_whenSplitUsingWhiteSpaceRegex_shouldGetExpectedResult() {
        String whitespaceRegex = SPACE + "|" + TAB + "|" + NEW_LINE;
        String[] allSamples = new String[] { FRUITS_SPACE_SEPARATED, FRUITS_TAB_SEPARATED, FRUITS_NEWLINE_SEPARATED };
        for (String fruits : allSamples) {
            String[] fruitArray = fruits.split(whitespaceRegex);
            verifySplit(fruitArray);
        }
    }

    @Test
    public void givenNewlineSeparatedString_whenSplitUsingWhiteSpaceMetaChar_shouldGetExpectedResult() {
        String whitespaceMetaChar = "\\s";
        String[] allSamples = new String[] { FRUITS_SPACE_SEPARATED, FRUITS_TAB_SEPARATED, FRUITS_NEWLINE_SEPARATED };
        for (String fruits : allSamples) {
            String[] fruitArray = fruits.split(whitespaceMetaChar);
            verifySplit(fruitArray);
        }
    }

    @Test
    public void givenSpaceSeparatedString_whenSplitUsingStringTokenizer_shouldGetExpectedResult() {
        String fruits = FRUITS_SPACE_SEPARATED;
        StringTokenizer tokenizer = new StringTokenizer(fruits);
        String[] fruitArray = new String[tokenizer.countTokens()];
        int index = 0;
        while (tokenizer.hasMoreTokens()) {
            fruitArray[index++] = tokenizer.nextToken();
        }
        verifySplit(fruitArray);
    }

    @Test
    public void givenWhiteSpaceSeparatedString_whenSplitUsingStringUtils_shouldGetExpectedResult() {
        String[] allSamples = new String[] { FRUITS_SPACE_SEPARATED, FRUITS_TAB_SEPARATED, FRUITS_NEWLINE_SEPARATED };
        for (String fruits : allSamples) {
            String[] fruitArray = StringUtils.split(fruits);
            verifySplit(fruitArray);
        }
    }

    @Test
    public void givenNullString_whenSplitUsingStringUtils_shouldReturnNull() {
        String fruits = null;
        String[] fruitArray = StringUtils.split(fruits);
        assertNull(fruitArray);
    }

    private void verifySplit(String[] fruitArray) {
        assertEquals(4, fruitArray.length);
        assertEquals("Apple", fruitArray[0]);
        assertEquals("Banana", fruitArray[1]);
        assertEquals("Mango", fruitArray[2]);
        assertEquals("Orange", fruitArray[3]);
    }
}
