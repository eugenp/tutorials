package com.baeldung.junit5.testinstance;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class TweetSerializerUnitTest {

    private String largeContent;
    private String content;
    private String smallContent;

    private Tweet tweet;

    @BeforeAll
    void setUpFixture() throws IOException {
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
        smallContent = "Lorem ipsum dolor";
        largeContent = new String(Files.readAllBytes(Paths.get("src/test/resources/lorem-ipsum.txt")));
        tweet = new Tweet();
        tweet.setId("AX1346");
    }

    @Test
    void serializerThrowsExceptionWhenMessageIsTooLarge() throws IOException {
        tweet.setContent(largeContent);

        TweetException tweetException = assertThrows(TweetException.class, () -> new TweetSerializer(tweet).serialize());
        assertThat(tweetException.getMessage(), is(equalTo("Tweet is too large")));
    }

    @Test
    void serializerThrowsExceptionWhenMessageIsTooSmall() throws IOException {
        tweet.setContent(smallContent);

        TweetException tweetException = assertThrows(TweetException.class, () -> new TweetSerializer(tweet).serialize());
        assertThat(tweetException.getMessage(), is(equalTo("Tweet is too small")));
    }

    @Test
    void serializeTweet() throws IOException {
        tweet.setContent(content);
        byte[] content = new TweetSerializer(tweet).serialize();
        assertThat(content, is(notNullValue()));
    }

}
