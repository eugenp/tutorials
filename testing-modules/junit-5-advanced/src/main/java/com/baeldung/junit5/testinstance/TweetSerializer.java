package com.baeldung.junit5.testinstance;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TweetSerializer {

    private static final int MAX_TWEET_SIZE = 1000;
    private static final int MIN_TWEET_SIZE = 150;
    private final Tweet tweet;

    public TweetSerializer(Tweet tweet) {
        this.tweet = tweet;
    }

    public byte[] serialize() throws IOException {
        byte[] tweetContent = serializeTweet();
        int totalLength = tweetContent.length;
        validateSizeOfTweet(totalLength);
        return tweetContent;
    }

    private void validateSizeOfTweet(int tweetSize) {
        if (tweetSize > MAX_TWEET_SIZE) {
            throw new TweetException("Tweet is too large");
        }
        if (tweetSize < MIN_TWEET_SIZE) {
            throw new TweetException("Tweet is too small");
        }
    }

    private byte[] serializeTweet() throws IOException {
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(); 
                ObjectOutputStream objectStream = new ObjectOutputStream(arrayOutputStream)) {
            objectStream.writeObject(tweet);
            return arrayOutputStream.toByteArray();
        }
    }

}
