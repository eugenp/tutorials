package com.baeldung.removeallcharsbeforechar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RemoveAllCharsBeforeSpecificOneUnitTest {
    String inputString = "Hello World!";
    char targetCharacter = 'W';

    @Test
    public void givenString_whenUsingSubstring_thenCharactersRemoved() {
        int index = inputString.indexOf(targetCharacter);

        if (index != -1) {
            String result = inputString.substring(index);
            assertEquals("World!", result);
        } else {
            assertEquals(inputString, inputString);
        }
    }

    @Test
    public void givenString_whenUsingRegex_thenCharactersRemoved() {
        int index = inputString.indexOf(targetCharacter);

        if (index != -1) {
            String result = targetCharacter + inputString.replaceAll(".*" + targetCharacter, "");
            assertEquals("World!", result);
        } else {
            assertEquals(inputString, inputString);
        }
    }

    @Test
    public void givenString_whenUsingStringBuilder_thenCharactersRemoved() {
        StringBuilder sb = new StringBuilder(inputString);
        int index = sb.indexOf(String.valueOf(targetCharacter));
        if (index != -1) {
            sb.delete(0, index);
        }
        assertEquals("World!", sb.toString());
    }
}
