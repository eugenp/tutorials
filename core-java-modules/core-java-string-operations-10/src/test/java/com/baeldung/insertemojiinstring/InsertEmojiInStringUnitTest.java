package com.baeldung.insertemojiinstring;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsertEmojiInStringUnitTest {
    String expected = "Java Tutorials and Guides at Baeldung. 😀";
    int smileyCodePoint = 0x1F600;

    @Test
    public void givenUnicodeEscape_whenInsertEmoji_thenCorrectString() {
        String textWithEmoji = "Java Tutorials and Guides at Baeldung. \uD83D\uDE00";

        assertEquals(expected, textWithEmoji);
    }

    @Test
    public void givenCodePoint_whenConvertToEmoji_thenCorrectString() {
        String textWithEmoji = "Java Tutorials and Guides at Baeldung. " + new String(Character.toChars(smileyCodePoint));

        assertEquals(expected, textWithEmoji);
    }

    @Test
    public void givenStringBuilder_whenAppendEmoji_thenCorrectString() {
        StringBuilder sb = new StringBuilder("Java Tutorials and Guides at Baeldung. ");
        sb.append(Character.toChars(smileyCodePoint));
        String textWithEmoji = sb.toString();

        assertEquals(expected, textWithEmoji);
    }

}
