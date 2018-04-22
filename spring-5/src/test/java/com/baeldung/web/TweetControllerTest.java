package com.baeldung.web;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.Spring5Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TweetControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getTweetById_whenDefaultTweetId_thenReturnsDefaultTweet() {
        ResponseEntity<Tweet> response = getTweetById("1");
        assertEquals(OK, response.getStatusCode());
        assertEquals("default tweet", response.getBody()
            .getText());
    }

    @Test
    public void addTweet_whenSendingValidTweet_thenReturnsSuccess() {
        Tweet tweet = Tweet.createTweet("2", "My first sample tweet");
        ResponseEntity<Void> addTweetResponse = restTemplate.postForEntity("/tweet", tweet, Void.class);
        assertEquals(OK, addTweetResponse.getStatusCode());

        ResponseEntity<Tweet> getTweetResponse = getTweetById("2");
        assertEquals(OK, getTweetResponse.getStatusCode());
        assertEquals("2", getTweetResponse.getBody()
            .getId());
        assertEquals("My first sample tweet", getTweetResponse.getBody()
            .getText());
    }

    @Test
    public void addTweet_whenSendingBlankId_thenReturnsError() {
        Tweet tweet = Tweet.createTweet("", "testTweet");
        assertUnProcessedEntity(tweet);
    }

    @Test
    public void addTweet_whenSendingBlankText_thenReturnsError() {
        Tweet tweet = Tweet.createTweet("3", "");
        assertUnProcessedEntity(tweet);
    }

    private ResponseEntity<Tweet> getTweetById(String tweetId) {
        return restTemplate.getForEntity("/tweet/" + tweetId, Tweet.class);
    }

    private void assertUnProcessedEntity(Tweet tweet) {
        ResponseEntity<Void> addTweetResponse = restTemplate.postForEntity("/tweet", tweet, Void.class);
        assertEquals(UNPROCESSABLE_ENTITY, addTweetResponse.getStatusCode());
    }
}
