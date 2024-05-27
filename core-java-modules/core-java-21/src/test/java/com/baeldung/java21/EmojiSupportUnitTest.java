package com.baeldung.java21;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class EmojiSupportUnitTest {

    @Test
    void whenMessageContainsEmoji_thenIsEmojiReturnsTrue() {
        String messageWithEmoji = "Hello Java 21! 😄";
        String messageWithoutEmoji = "Hello Java!";

        assertTrue(messageWithEmoji.codePoints().anyMatch(Character::isEmoji));
        assertFalse(messageWithoutEmoji.codePoints().anyMatch(Character::isEmoji));
    }

    @Test
    void whenMessageContainsEmojiPresentation_thenIsEmojiPresentationReturnsTrue() {
        String emojiPresentationMessage = "Hello Java 21! 🔥😄";
        String nonEmojiPresentationMessage = "Hello Java 21!️";

        assertTrue(emojiPresentationMessage.codePoints().anyMatch(Character::isEmojiPresentation));
        assertFalse(nonEmojiPresentationMessage.codePoints().anyMatch(Character::isEmojiPresentation));
    }

    @Test
    void whenCharacterIsEmojiModifier_thenIsEmojiModifierReturnsTrue() {
        assertTrue(Character.isEmojiModifier(0x1F3FB)); // light skin tone
        assertTrue(Character.isEmojiModifier(0x1F3FD)); // medium skin tone
        assertTrue(Character.isEmojiModifier(0x1F3FF)); // dark skin tone
    }

    @Test
    void whenCharacterIsEmojiModifierBase_thenIsEmojiModifierBaseReturnsTrue() {
        assertTrue(Character.isEmojiModifierBase(Character.codePointAt("👍", 0)));
        assertTrue(Character.isEmojiModifierBase(Character.codePointAt("👶", 0)));

        assertFalse(Character.isEmojiModifierBase(Character.codePointAt("🍕", 0)));
    }

    @Test
    void whenCharacterIsEmojiComponent_thenIsEmojiComponentReturnsTrue() {
        assertTrue(Character.isEmojiComponent(0x200D)); // Zero width joiner
        assertTrue(Character.isEmojiComponent(0x1F3FD)); // medium skin tone
    }

    @Test
    void whenCharacterIsExtendedPictographic_thenIsExtendedPictographicReturnsTrue() {
        assertTrue(Character.isExtendedPictographic(Character.codePointAt("☀", 0))); // Sun with rays
        assertTrue(Character.isExtendedPictographic(Character.codePointAt("✔", 0))); // Checkmark
    }

    @Test
    void whenMessageContainsEmoji_thenEmojiRegexMatches() {
        String messageWithEmoji = "Hello Java 21! 😄";

        Matcher isEmojiMatcher = Pattern.compile("\\p{IsEmoji}").matcher(messageWithEmoji);
        Matcher isEmojiPresentationMatcher = Pattern.compile("\\p{IsEmoji_Presentation}").matcher(messageWithEmoji);;

        assertTrue(isEmojiMatcher.find());
        assertTrue(isEmojiPresentationMatcher.find());
        
        String messageWithoutEmoji = "Hello Java!";
        isEmojiMatcher = Pattern.compile("\\p{IsEmoji}").matcher(messageWithoutEmoji);
        assertFalse(isEmojiMatcher.find());
    }

}
