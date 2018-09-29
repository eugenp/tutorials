package com.baeldung.string;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.vdurmont.emoji.EmojiParser;

public class RemovingEmojiFromStringUnitTest {
    String text = "la conférence, commencera à 10 heures 😅";
    String regex = "[^\\p{L}\\p{N}\\p{P}\\p{Z}]";

    @Test
    public void whenRemoveEmojiUsingLibrary_thenSuccess() {
        String result = EmojiParser.removeAllEmojis(text);
        System.out.println(result);
        assertEquals(result, "la conférence, commencera à 10 heures ");
    }
    
    @Test
    public void whenReplaceEmojiUsingLibrary_thenSuccess() {
        String result = EmojiParser.parseToAliases(text);
        System.out.println(result);
        assertEquals(result, "la conférence, commencera à 10 heures :sweat_smile:");
    }

    @Test
    public void whenRemoveEmojiUsingRegex_thenSuccess() {
        String result = text.replaceAll(regex, "");
        System.out.println(result);
        assertEquals(result, "la conférence, commencera à 10 heures ");
    }

    @Test
    public void whenRemoveEmojiUsingMatcher_thenSuccess() {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(text);
        
        String result = matcher.replaceAll("");
        System.out.println(result);
        assertEquals(result, "la conférence, commencera à 10 heures ");
    }

    @Test
    public void whenRemoveEmojiUsingCodepoints_thenSuccess() {
        String result = text.replaceAll("[\\x{0001f300}-\\x{0001f64f}]|[\\x{0001f680}-\\x{0001f6ff}]", "");
        System.out.println(result);
        assertEquals(result, "la conférence, commencera à 10 heures ");
    }

    @Test
    public void whenRemoveEmojiUsingUnicode_thenSuccess() {
        String result = text.replaceAll("[\ud83c\udf00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]", "");
        System.out.println(result);
        assertEquals(result, "la conférence, commencera à 10 heures ");
    }
}
