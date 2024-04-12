package com.baeldung.asciiart;

import java.awt.Font;

import org.junit.jupiter.api.Test;

import com.baeldung.asciiart.AsciiArt.Settings;

public class AsciiArtIntegrationTest {

    @Test
    public void givenTextWithAsciiCharacterAndSettings_shouldPrintAsciiArt() {
        AsciiArt asciiArt = new AsciiArt();
        String text = "BAELDUNG";
        Settings settings = asciiArt.new Settings(new Font("SansSerif", Font.BOLD, 24), text.length() * 30, 30); // 30 pixel width per character

        asciiArt.drawString(text, "*", settings);
    }
}
