package com.baeldung.findemojis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.vdurmont.emoji.EmojiManager;

public class FindEmojisUnitTest {

    @Test
    public void givenAWord_whenUsingEmojiJava_thenDetectEmoji() {
        boolean emoji = EmojiManager.isEmoji("\uD83D\uDC3B");
        assertTrue(emoji);

        boolean notEmoji = EmojiManager.isEmoji("w");
        assertFalse(notEmoji);
    }

    @Test
    public void givenAWord_whenUsingRegex_thenDetectEmoji() {
        String regexPattern = "[\uD800-\uDBFF\uDC00-\uDFFF]+";
        String emojiString = "\uD83D\uDC3B";
        boolean emoji = emojiString.matches(regexPattern);
        assertTrue(emoji);

        String notEmojiString = "w";
        boolean notEmoji = notEmojiString.matches(regexPattern);
        assertFalse(notEmoji);
    }

}
