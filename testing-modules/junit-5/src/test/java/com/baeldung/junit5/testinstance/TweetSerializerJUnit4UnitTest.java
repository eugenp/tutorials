package com.baeldung.junit5.testinstance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class TweetSerializerJUnit4UnitTest {

    private static String largeContent;
    private static String content;
    private static String smallContent;

    private static Tweet tweet;

    @BeforeClass
    public static void setUpFixture() throws IOException {
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
        smallContent = "Lorem ipsum dolor";
        largeContent = new String(Files.readAllBytes(Paths.get("src/test/resources/lorem-ipsum.txt")));
        tweet = new Tweet();
        tweet.setId("AX1346");
    }

    @Test
    public void serializerThrowsExceptionWhenMessageIsTooLarge() throws IOException {
        tweet.setContent(largeContent);

        TweetException tweetException = assertThrows(TweetException.class, () -> new TweetSerializer(tweet).serialize());
        assertTrue(tweetException.getMessage()
            .contains("Tweet is too large"));
    }

    @Test
    public void serializerThrowsExceptionWhenMessageIsTooSmall() throws IOException {
        tweet.setContent(smallContent);

        TweetException tweetException = assertThrows(TweetException.class, () -> new TweetSerializer(tweet).serialize());
        assertTrue(tweetException.getMessage()
            .contains("Tweet is too small"));
    }

    @Test
    public void serializeTweet() throws IOException {
        tweet.setContent(content);
        byte[] content = new TweetSerializer(tweet).serialize();
        assertThat(content, is(notNullValue()));
    }
}
