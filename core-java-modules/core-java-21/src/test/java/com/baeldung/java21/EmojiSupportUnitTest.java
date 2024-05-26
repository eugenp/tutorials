package com.baeldung.java21;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class EmojiSupportUnitTest {

    @Test
    void when_message_contains_emoji_then_isEmoji_returns_true() {
        String messageWithEmoji = "Hello Java 21! 😄";
        String messageWithoutEmoji = "Hello Java!";

        assertTrue(messageWithEmoji.codePoints().anyMatch(Character::isEmoji));
        assertFalse(messageWithoutEmoji.codePoints().anyMatch(Character::isEmoji));
    }

    @Test
    void when_message_contains_emoji_presentation_then_isEmojiPresentation_returns_true() {
        String emojiPresentationMessage = "Hello Java 21! 🔥😄";
        String nonEmojiPresentationMessage = "Hello Java 2️⃣1️⃣❗️";

        assertTrue(emojiPresentationMessage.codePoints().anyMatch(Character::isEmojiPresentation));
        assertFalse(nonEmojiPresentationMessage.codePoints().anyMatch(Character::isEmojiPresentation));
    }

    @Test
    void when_character_is_emoji_modifier_then_isEmojiModifier_returns_true() {
        assertTrue(Character.isEmojiModifier(0x1F3FB)); // light skin tone
        assertTrue(Character.isEmojiModifier(0x1F3FD)); // medium skin tone
        assertTrue(Character.isEmojiModifier(0x1F3FF)); // dark skin tone
    }

    @Test
    void when_character_is_emoji_modifier_base_then_isEmojiModifierBase_returns_true() {
        assertTrue(Character.isEmojiModifierBase(Character.codePointAt("👍", 0)));
        assertTrue(Character.isEmojiModifierBase(Character.codePointAt("👶", 0)));

        assertFalse(Character.isEmojiModifierBase(Character.codePointAt("🍕", 0)));
    }

    @Test
    void when_character_is_emoji_component_then_isEmojiComponent_returns_true() {
        assertTrue(Character.isEmojiComponent(0x200D)); // Zero width joiner
        assertTrue(Character.isEmojiComponent(0x1F3FD)); // medium skin tone
    }

    @Test
    void when_character_is_extended_pictographic_then_isExtendedPictographic_returns_true() {
        assertTrue(Character.isExtendedPictographic(Character.codePointAt("☀", 0))); // Sun with rays
        assertTrue(Character.isExtendedPictographic(Character.codePointAt("✔", 0))); // Checkmark
    }

    @Test
    void testEmojiRegex() {
        String messageWithEmoji = "Hello Java 21! 😄";

        Matcher isEmojiMatcher = Pattern.compile("\\p{IsEmoji}").matcher(messageWithEmoji);
        Matcher isEmojiPresentationMatcher = Pattern.compile("\\p{IsEmoji_Presentation}").matcher(messageWithEmoji);;

        assertTrue(isEmojiMatcher.find());
        assertTrue(isEmojiPresentationMatcher.find());
        
        String messageWithoutEmoji = "Hello Java 21";
        isEmojiMatcher = Pattern.compile("\\p{IsEmoji}").matcher(messageWithoutEmoji);
        assertFalse(isEmojiMatcher.find());
    }

}
