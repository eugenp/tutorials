package com.baeldung.centertext;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class CenteringTextUnitTest {

    @Test
    public void givenTextAndTotalWidth_whenUsingStringFormat_thenTextIsCentered() {
        String text = "Centered Text";
        int totalWidth = 20;
        int padding = (totalWidth - text.length()) / 2;
        String centeredText = String.format("%" + padding + "s%s%" + padding + "s", "", text, "");
        String expectedCenteredText = "   Centered Text   ";
        Assert.assertEquals("Centered Text", expectedCenteredText, centeredText);
    }

    @Test
    public void givenTextAndTotalWidth_whenCenterUsingStringBuilder_thenTextIsCentered() {
        String text = "Centered Text";
        int width = 20;
        int padding = (width - text.length()) / 2;
        StringBuilder centeredText = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            centeredText.append(" ");
        }
        centeredText.append(text);
        for (int i = 0; i < padding; i++) {
            centeredText.append(" ");
        }
        String centeredTextString = centeredText.toString();
        String expectedCenteredText = "   Centered Text   ";
        Assert.assertEquals("Centered Text", expectedCenteredText, centeredTextString);
    }

}
