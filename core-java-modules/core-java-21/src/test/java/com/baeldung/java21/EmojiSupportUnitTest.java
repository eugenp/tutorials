package com.baeldung.java21;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class EmojiSupportUnitTest {

    @Test
    public void testIsEmoji() {
        String messageWithEmoji = "Hello Java 21! 😄";
        String messageWithoutEmoji = "Hello Java!";

        assertTrue(messageWithEmoji.codePoints().anyMatch(Character::isEmoji));
        assertFalse(messageWithoutEmoji.codePoints().anyMatch(Character::isEmoji));
    }

    @Test
    public void testIsEmojiPresentation() {
        String emojiPresentationMessage = "Hello Java 21! 🔥😄";
        String nonEmojiPresentationMessage = "Hello Java 2️⃣1️⃣❗️";

        assertTrue(emojiPresentationMessage.codePoints().anyMatch(Character::isEmojiPresentation));
        assertFalse(nonEmojiPresentationMessage.codePoints().anyMatch(Character::isEmojiPresentation));
    }

    @Test
    public void testIsEmojiModifier() {
        assertTrue(Character.isEmojiModifier(0x1F3FB)); // light skin tone
        assertTrue(Character.isEmojiModifier(0x1F3FD)); // medium skin tone
        assertTrue(Character.isEmojiModifier(0x1F3FF)); // dark skin tone
    }

    @Test
    public void testIsEmojiModifierBase() {
        assertTrue(Character.isEmojiModifierBase(0x1F44D)); // Thumbs up 👍
        assertTrue(Character.isEmojiModifierBase(0x1F476)); // Baby 👶

        assertFalse(Character.isEmojiModifierBase(0x1F355)); // Pizza 🍕
    }

    @Test
    public void testIsEmojiComponent() {
        assertTrue(Character.isEmojiComponent(0x200D)); // Zero width joiner
        assertTrue(Character.isEmojiComponent(0x1F3FD)); // medium skin tone
    }

    @Test
    public void testIsExtendedPictographic() {
        assertTrue(Character.isExtendedPictographic(0x2600)); // Sun with rays ☀
        assertTrue(Character.isExtendedPictographic(0x2714)); // Check mark ✔️
    }

    @Test
    public void testEmojiRegex() {
        String messageWithEmoji = "Hello Java 21! 😄";

        Matcher isEmojiMatcher = Pattern.compile("\\p{IsEmoji}").matcher(messageWithEmoji);
        Matcher isEmojiPresentationMatcher = Pattern.compile("\\p{IsEmoji_Presentation}").matcher(messageWithEmoji);;

        assertTrue(isEmojiMatcher.find());
        assertTrue(isEmojiPresentationMatcher.find());
    }

}
